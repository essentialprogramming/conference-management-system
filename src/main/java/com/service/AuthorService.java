package com.service;

import com.entities.AuthorEntity;
import com.entities.EvaluationEntity;
import com.mapper.PaperMapper;
import com.output.PaperJSON;
import com.repository.AuthorRepository;
import com.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private EvaluationRepository evaluationRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public List<PaperJSON> getPapersOfAuthor(String email)
    {

        Optional<AuthorEntity> author = authorRepository.findById(email);
        if(author.isPresent())
        {

            return (author.get().getPapers() != null ? author.get().getPapers().stream()
                 .map(PaperMapper::entityToPaper)
                 .collect(Collectors.toList()) : new ArrayList<>());
        }
        else{
            return new ArrayList<>();
        }

    }

    @Transactional
    public List<PaperJSON> getAcceptedPapersOfAuthor(String email)
    {

        Optional<AuthorEntity> author = authorRepository.findById(email);
        if(author.isPresent())
        {

            return (author.get().getPapers() != null ? author.get().getPapers().stream()
                    .filter(paper -> {

                        Set<EvaluationEntity> evals = paper.getReviews().keySet();

                        if(evals.size()<=2)
                            return false;
                        for(EvaluationEntity evaluation : evals)
                        {
                            if(evaluation.getQualifier().getValue().contains("REJECT"))
                                return false;
                        }
                        return true;
                    })
                    .map(PaperMapper::entityToPaper)
                    .collect(Collectors.toList()) : new ArrayList<>());
        }
        else{
            return new ArrayList<>();
        }

    }
}
