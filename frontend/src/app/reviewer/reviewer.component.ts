import { Component, OnInit } from '@angular/core';
import {Paper} from '../models/paper';
import { AuthService } from '../AuthentificatrionService/auth.service';

@Component({
  selector: 'app-reviewer',
  templateUrl: './reviewer.component.html',
  styleUrls: ['./reviewer.component.css']
})
export class ReviewerComponent implements OnInit {
  title: String;
  title2: String;

  papers: Paper[];


  constructor(private auth:AuthService) {
    this.title = 'Reviewer';
    this.title2 = 'Papers';
  }

  ngOnInit() {
    this.title = this.auth.getName();
  }
onclick() {
    return '$(\'#paper\').click(function(){$(this).data(\'clicked\', false);})';
}
}
