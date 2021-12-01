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

    private final AuthorRepository authorRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, EvaluationRepository evaluationRepository) {
        this.authorRepository = authorRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @Transactional
    public List<PaperJSON> getPapersOfAuthor(String email) {

        Optional<AuthorEntity> author = authorRepository.findById(email);
        return author.filter(authorEntity -> authorEntity.getPapers() != null).map(authorEntity -> authorEntity.getPapers().stream()
                .map(PaperMapper::entityToPaper)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);

    }

    @Transactional
    public List<PaperJSON> getAcceptedPapersOfAuthor(String email) {

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
