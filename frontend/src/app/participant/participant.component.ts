import { Component, OnInit } from '@angular/core';
import { Paper } from '../models/paper';
import { Conference } from '../models/conference';
import { Program } from '../models/Program';
import { Location } from '../models/Location';
import { ActivatedRoute, Router } from '@angular/router';
import { ConferenceChairServiceService } from '../Shared/conference-chair-service.service';
import { Section } from '../models/section';
import { PcMember } from '../models/pcmember';

@Component({
  selector: 'app-participant',
  templateUrl: './participant.component.html',
  styleUrls: ['./participant.component.css']
})
export class ParticipantComponent implements OnInit {
  paper:Paper;
  conference: Conference;
  location: Location;
  program: Program;
  errorMessage: string;
  name:string;
  pcMembers: PcMember[];
  sections: Section[];
  mySections: Section;
  sectiones:string[];
  constructor(
    private route: ActivatedRoute,
              private router: Router,
              private service: ConferenceChairServiceService
  ) { 
    this.location = new Location(null,null,null,null);
    this.program = new Program(null,null,null,null,null,null);
    this.conference = new Conference(null,null,this.location,null,this.program,null,null,this.sectiones);
    this.name = localStorage.getItem('email');
  }

  ngOnInit() {
    //this.paper= new Paper(1, 'a', 'b','c','b','d','v');
    this.getConference();
    this.getConferencesSections();
    this.getPcMembers();

  }

  getConference() {
    this.service.getConference(1)
      .subscribe(
        conference =>  this.conference = conference,
        error => this.errorMessage = <any>error
      );
  }
  getConferencesSections() {
    this.service.getConference(1)
      .subscribe(
        events => this.getSections(events),
        error => this.errorMessage = <any>error
      );
  }

  getSections(events) {
    console.log(events);
    this.sections = events.sections;
  }

  getPcMembers(){
    this.service.findAllPcMembers().subscribe(res=> this.pcMembers = res);
  }
}
