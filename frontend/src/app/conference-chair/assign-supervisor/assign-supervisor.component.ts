import { Component, OnInit } from '@angular/core';
import {PcMember} from '../../models/pcmember';
import {Section} from '../../models/Section';
import {ConferenceChairServiceService} from '../../shared/conference-chair-service.service';
import {Conference} from '../../models/Conference';
import {PcMem} from '../../models/PcMem';

@Component({
  selector: 'app-assign-supervisor',
  templateUrl: './assign-supervisor.component.html',
  styleUrls: ['./assign-supervisor.component.css']
})
export class AssignSupervisorComponent implements OnInit {

  selectedRow : Number;
  selectedRowPC: Number;
  setClickedRowPaper : Function;
  setClickedRowPC : Function;

  events : Conference[];
  pcMembers: PcMember[];
  pcMem: string[];
  sections: Section[];
  title: string;
  title1: string;
  title2: string;
  pcMember: any;
  i: any;
  ok: boolean;
  errorMessage: string;
  selectedPcMember: PcMember;
  selectedSection: Section;
  constructor(private service: ConferenceChairServiceService) {
    this.title = 'Choose supervisor';
    this.title1 = 'Pc Member';
    this.title2 = 'Section';
  }

  ngOnInit() {
this.getPcMembers();
this.getConferences();
this.setClickedRowPaper = function(index){
  this.selectedRow = index;
}
this.setClickedRowPC = function(index){
  this.selectedRowPC = index;
}
  }

  assign() {
    this.service.assignSupervisortoSection(this.selectedPcMember.email,this.selectedSection.id,this.selectedSection);
  }

  getPcMembers() {
    this.service.findAllPcMembers()
      .subscribe(
        members => this.pcMembers = members,
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
    this.sections = events[0].sections;
  }

  rowSelectedSections(section: Section) {
    this.selectedSection = section;
  }

  rowSelectedPcMember(pcMember: PcMember) {
    this.selectedPcMember = pcMember;
  }
}
