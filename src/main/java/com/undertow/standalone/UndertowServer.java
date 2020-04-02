package com.undertow.standalone;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;

import com.config.ContextLoaderListenerInstanceFactory;
import io.undertow.servlet.api.ListenerInfo;
import org.glassfish.jersey.servlet.ServletContainer;

import com.config.ApplicationConfig;
import com.server.Server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.StuckThreadDetectionHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.WebSocketProtocolHandshakeHandler;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import org.springframework.web.context.ContextLoaderListener;

import static com.util.cloud.DeploymentConfiguration.getProperty;
import static io.undertow.Handlers.websocket;

public final class UndertowServer {

    public final Lock LOCK = new ReentrantLock();

    private final String host;
    private final int port;
    private final String deploymentName;

    private volatile Undertow server;

    public UndertowServer(String host, int port, String deploymentName) {
        this.host = host;
        this.port = port;
        this.deploymentName = deploymentName;
    }

    private static ListenerInfo createContextLoaderListener() {
        return new ListenerInfo(ContextLoaderListener.class,
                new ContextLoaderListenerInstanceFactory());

    }

    private HttpHandler bootstrap() throws ServletException {
        final DeploymentInfo servletBuilder = deployment()
                .setClassLoader(Server.class.getClassLoader())
                .setContextPath("/")
                .addListeners(createContextLoaderListener())
                .setResourceManager(new ClassPathResourceManager(Server.class.getClassLoader(), "webapp/resources"))
                .addWelcomePage("index.html")
                .setDeploymentName(deploymentName)
                .addServlets(
                        servlet("jerseyServlet", ServletContainer.class)
                                .addInitParam("javax.ws.rs.Application", ApplicationConfig.class.getName())
                                .addMapping("/api/*")
                                .setLoadOnStartup(1)
                                .setAsyncSupported(true));

        final DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        //Servlet handler
        final HttpHandler servletHandler = manager.start();

        //Open API resource handler
        final ResourceHandler resourceHandler = new ResourceHandler(new ClassPathResourceManager(Server.class.getClassLoader(), "apidoc"))
                .addWelcomeFiles("index.html")
                .setDirectoryListingEnabled(false);


        final PathHandler pathHandler = Handlers.path()
                .addPrefixPath("/", servletHandler)
                .addPrefixPath("apidoc", resourceHandler);

        return pathHandler;
    }


    public void start() throws ServletException {

        final HttpHandler httpHandler = bootstrap();
        final StuckThreadDetectionHandler stuck = new StuckThreadDetectionHandler(getProperty("thread.execution.time", 100), httpHandler);
        final GracefulShutdownHandler shutdown = Handlers.gracefulShutdown(stuck);

        LOCK.lock();

        server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(shutdown)
                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .build();

        server.start();
    }


    public void stop() {
        server.stop();
        LOCK.unlock();
    }
}
