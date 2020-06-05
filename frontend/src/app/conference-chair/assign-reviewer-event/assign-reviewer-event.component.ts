import { Component, OnInit } from '@angular/core';
import {Conference} from '../../models/conference';
import {PcMember} from '../../models/pcmember';
import {ConferenceChairServiceService} from '../../Shared/conference-chair-service.service';

@Component({
  selector: 'app-assign-reviewer-event',
  templateUrl: './assign-reviewer-event.component.html',
  styleUrls: ['./assign-reviewer-event.component.css']
})
export class AssignReviewerEventComponent implements OnInit {

  selectedRow : Number;
  selectedRowPC: Number;
  setClickedRowPaper : Function;
  setClickedRowPC : Function;

  events: Conference[];
  pcMembers: PcMember[];
  selectedPcMember: PcMember;
  selectedEvent: Conference;
  errorMessage: string;
  constructor(private service: ConferenceChairServiceService) { }

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
  getPcMembers() {
    this.service.unassignedPcMembers()
      .subscribe(
        members => this.pcMembers = members,
        error => this.errorMessage = <any>error
      );
  }
  RowSelectedPcMembers(pcMember: PcMember) {
    this.selectedPcMember = pcMember;
  }

  RowSelectedEvent(event: Conference) {
    this.selectedEvent = event;
  }
  getConferences() {
    this.service.findAll()
      .subscribe(
        events => this.events = events,
        error => this.errorMessage = <any>error
      );
  }
  assign() {
    this.service.assignReviewerToEvent(this.selectedEvent,this.selectedEvent.id,this.selectedPcMember.email).subscribe(res => console.log(res));
  }
}
