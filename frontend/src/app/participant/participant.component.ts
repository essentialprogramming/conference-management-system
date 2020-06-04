import { Component, OnInit } from '@angular/core';
import { Paper } from '../models/paper';
import { Conference } from '../models/conference';
import { Program } from '../models/Program';
import { Location } from '../models/Location';
import { ActivatedRoute, Router } from '@angular/router';
import { ConferenceChairServiceService } from '../Shared/conference-chair-service.service';

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
  constructor(
    private route: ActivatedRoute,
              private router: Router,
              private service: ConferenceChairServiceService
  ) { 
    this.location = new Location(null,null,null,null);
    this.program = new Program(null,null,null,null,null,null);
    this.conference = new Conference(null,null,this.location,null,this.program,null,null,null);
    this.name = localStorage.getItem('email');
  }

  ngOnInit() {
    //this.paper= new Paper(1, 'a', 'b','c','b','d','v');
    this.getConference();


  }

  getConference() {
    this.service.getConference(1)
      .subscribe(
        conference => this.conference = conference,
        error => this.errorMessage = <any>error
      );
  }
}
