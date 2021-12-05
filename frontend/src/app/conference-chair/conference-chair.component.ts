import { Component, OnInit } from '@angular/core';
import {PcMember} from '../models/pcmember';
import { AuthService } from '../AuthentificationService/auth.service';

@Component({
  selector: 'app-conference-chair',
  templateUrl: './conference-chair.component.html',
  styleUrls: ['./conference-chair.component.css']
})
export class ConferenceChairComponent implements OnInit {
  title: String;

  constructor(private auth:AuthService) {
    this.title = 'Conference Chair';

  }

  ngOnInit() {
    this.title=this.auth.getName();
  }

}
