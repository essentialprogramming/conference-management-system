import { Component, OnInit } from '@angular/core';
import {Paper} from '../../models/Paper';
import {Section} from '../../models/Section';
import {ConferenceChairServiceService} from '../../shared/conference-chair-service.service';
import {Conference} from '../../models/Conference';

@Component({
  selector: 'app-assign-section',
  templateUrl: './assign-section.component.html',
  styleUrls: ['./assign-section.component.css']
})
export class AssignSectionComponent implements OnInit {

  selectedRow : Number;
  selectedRowPC: Number;
  setClickedRowPaper : Function;
  setClickedRowPC : Function;

  errorMessage: string;
papers: Paper[];
sections: Section[];
title: string;
title1: string;
title2: string;
ok: boolean;
i: any;
selectedSection: Section;
selectedPaper: Paper;
events: Conference[];

  constructor(private service: ConferenceChairServiceService) {
    this.title = 'Choose the papers Sections';
    this.title1 = 'Papers';
    this.title2 = 'Sections';
  }

  ngOnInit() {
    this.getPapers();
    this.getConferences();
    this.setClickedRowPaper = function(index){
      this.selectedRow = index;
    }
    this.setClickedRowPC = function(index){
      this.selectedRowPC = index;
    }

  }

  assign() {
    this.service.assignPapertoSection(this.selectedPaper.id,this.selectedSection.id,this.selectedSection).subscribe(res =>console.log(res));

  }


  getPapers() {
    this.service.findAllPapers()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }
  getConferences() {
    this.service.findAll()
      .subscribe(
        events => this.getSections(events),
        error => this.errorMessage = <any>error
      );
  }
  getSections(events) {
    console.log(events);
    this.sections = events[0].sections;

  }

  RowSelected(paper: Paper) {
    console.log(paper);
    this.selectedPaper = paper;
  }

  RowSelectedSection(section: Section) {
    console.log(section);
    this.selectedSection = section;
  }
}
