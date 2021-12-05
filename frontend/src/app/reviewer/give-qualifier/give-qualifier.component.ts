import { Component, OnInit } from '@angular/core';
import {Paper} from '../../models/Paper';
import {ReviewerServiceService} from '../../shared/reviewer-service.service';
import {Qualifier} from '../../models/Qualifier';

@Component({
  selector: 'app-give-qualifier',
  templateUrl: './give-qualifier.component.html',
  styleUrls: ['./give-qualifier.component.css']
})
export class GiveQualifierComponent implements OnInit {
  title: String;
  title2: String;
  errorMessage: string;
  qu: Qualifier;

  papers: Paper[];
  constructor(private service: ReviewerServiceService) {
    this.title = 'Reviewer';
    this.title2 = 'Give Qualifier';

  }
  ngOnInit() {
    this.getPapers();
  }


  getPapers() {
    const email = localStorage.getItem('email');
    console.log(email);
    this.service.getAcceptedPapers(email)
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  qualifier(id: string, value: string) {
    console.log(id);
    console.log(value);
    console.log(localStorage.getItem('email'));
     this.qu = new Qualifier(value, 'a');
     this.service.qualifierR( id, this.qu).subscribe(data => {
       console.log('success');
     });
  }

}
