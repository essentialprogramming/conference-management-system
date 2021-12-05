import { Component, OnInit } from '@angular/core';
import {Paper} from '../../models/Paper';
import {ReviewerServiceService} from '../../shared/reviewer-service.service';

@Component({
  selector: 'app-bid-proposal',
  templateUrl: './bid-proposal.component.html',
  styleUrls: ['./bid-proposal.component.css']
})
export class BidProposalComponent implements OnInit {
  title: String;
  title2: String;
errorMessage: String;
  papers: Paper[];
  constructor(private service: ReviewerServiceService) {
    this.title = 'Reviewer';
    this.title2 = ' Bid Proposal';

  }
  ngOnInit() {
    this.getPapers();
  }


  getPapers() {
    this.service.findAll()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  bidP(id: string, value: string) {
    this.service.bid(id, value).subscribe(
      data => console.log(data)
    );

  }
}
