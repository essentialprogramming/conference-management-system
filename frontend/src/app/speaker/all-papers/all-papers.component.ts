import { Component, OnInit } from '@angular/core';
import { Paper } from '../../models/paper';
import { SpeakerServiceService } from '../../Shared/speaker-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-papers',
  templateUrl: './all-papers.component.html',
  styleUrls: ['./all-papers.component.css']
})
export class AllPapersComponent implements OnInit {

  errorMessage: string;
  selectedRow : Number;
    setClickedRow : Function;
  title: string;
  title2: string;
  papers: Paper[];
  selectedPaper: Paper;
  constructor(private speakerService: SpeakerServiceService, private router: Router) {
    this.title = 'Accepted papers';
    this.title2 = 'Accepted Papers';

  }


  
  ngOnInit() {

    this.getPapers();
    this.setClickedRow = function(index){
      this.selectedRow = index;
    }
  }


  getPapers() {
    this.speakerService.findAlAccepted()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }



  listClick(event, newValue, cell) {
    cell.isSelected = !cell.isSelected;
} 

}
