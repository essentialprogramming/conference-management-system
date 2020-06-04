import { Component, OnInit } from '@angular/core';
import {PcMember} from '../../models/pcmember';
import {Paper} from '../../models/paper';
import {ConferenceChairServiceService} from '../../Shared/conference-chair-service.service';
import {PcMem} from '../../models/PcMem';

@Component({
  selector: 'app-assign-papers',
  templateUrl: './assign-papers.component.html',
  styleUrls: ['./assign-papers.component.css']
})
export class AssignPapersComponent implements OnInit {
  title: string;
  ok: boolean;
  i: any;
  title2: string;
  pcMem: string[];
  pcMembers: PcMember[];
  papers: Paper[];
  selectedPaper: Paper;
  selectedPcMember: PcMember;
  errorMessage: string;
  constructor(private service: ConferenceChairServiceService) {
    this.title = 'PcMembers';
    this.title2 = 'Papers';
  }

  ngOnInit() {
    this.getPapers();
    // this.getPcMembers();
  }

  assign() {

    this.service.assignPaper(this.selectedPaper.id.toString(), this.selectedPcMember.email, this.selectedPaper).subscribe(res => console.log(res));
  }


  getPcMembers(id) {
    this.service.findPcMembersForPaper(id)
      .subscribe(
        members => this.pcMembers = members,

    error => this.errorMessage = <any>error
      );
    console.log(this.pcMembers);
  }


  getPapers() {
    this.service.findAllPapers()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  RowSelected(paper: Paper) {
    this.getPcMembers(paper.id);
    this.selectedPaper = paper;
    console.log(this.selectedPaper);
  }

  RowSelectedPcMembers(pcMember: PcMember) {
    this.selectedPcMember = pcMember;
    console.log(this.selectedPcMember);
  }
}
