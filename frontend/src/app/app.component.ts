import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { AuthService } from './AuthentificatrionService/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private auth:AuthService) {
  }

  ngOnInit() {


  }


}
