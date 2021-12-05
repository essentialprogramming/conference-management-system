import { Component, OnInit } from '@angular/core';
import {Paper} from '../../models/Paper';
import { SpeakerServiceService } from '../../shared/speaker-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-submissions',
  templateUrl: './my-submissions.component.html',
  styleUrls: ['./my-submissions.component.css']
})
export class MySubmissionsComponent implements OnInit {
  errorMessage: string;

  title: string;
  title2: string;
  papers: Paper[];
  selectedPaper: Paper;
  constructor(private speakerService: SpeakerServiceService, private router: Router) {
    this.title = 'My Submissions';
    this.title2 = 'My Papers';

  }

  ngOnInit() {
    this.getPapers();
  }


  getPapers() {
    this.speakerService.findAll()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  goEdit(paper: Paper){
    this.router.navigate(['/submitProposal', paper.id]).then(() => {
      window.location.reload();
  });
}
}

