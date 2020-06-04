import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../AuthentificatrionService/auth.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit{
  title: string;
  constructor(private router:ActivatedRoute, private auth:AuthService) {
    this.title = '';
  }
  ngOnInit(){
    this.title=this.auth.getName();
  }
}
