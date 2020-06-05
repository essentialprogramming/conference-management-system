package com.mapper;

import com.entities.AuthorEntity;
import com.entities.EvaluationEntity;
import com.entities.SectionEntity;
import com.output.PaperJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Utils {

    static List<PaperJSON> getAcceptedPapers(AuthorEntity author)
    {
        return author.getPapers().stream()
                .filter(paper -> {

                    List<EvaluationEntity> evals = paper.getReviews();

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
                .collect(Collectors.toList());
    }

    static List<String> getSpeakers(SectionEntity section)
    {
        List<List<AuthorEntity>> result = section.getPapers()
                .stream()
                .map(paper -> {
                    List<AuthorEntity> authors = paper.getAuthors();
                    List<AuthorEntity> acceptedAuthors = authors.stream()
                            .filter(a ->
                                    Utils.getAcceptedPapers(a).size() >= 1)
                            .collect(Collectors.toList());
                    return acceptedAuthors;
                })
                .collect(Collectors.toList());
        List<String> speakersList = new ArrayList<>();
        result.forEach(list ->{
            list.forEach(acceptedAuthor -> speakersList.add(acceptedAuthor.getEmail()));
        });
        return speakersList;
    }
}
