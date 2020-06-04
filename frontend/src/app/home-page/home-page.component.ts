import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../AuthentificatrionService/auth.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  constructor(private router: ActivatedRoute, private auth: AuthService) { }

  ngOnInit() {
    console.log(localStorage.getItem('role'));
    if(localStorage.getItem("token") === null && this.router.snapshot.queryParams['token']) {

    var tok = this.router.snapshot.queryParamMap.get('token');
    // var tok="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6InNwZWFrZXIiLCJpYXQiOjE1MTYyMzkwMjJ9.f_r1Qr232o_7ZZrbdBLuvrhXjA2hEiq8jaaPBucSSB0"
    this.auth.setToken(tok);
      
    window.location.reload();

  }

}


}
