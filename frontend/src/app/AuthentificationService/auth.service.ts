import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {

  private token = '';
  public  decoded = '';
  public r = '';
  public flag: string;

  constructor(private _router: Router) {
    // this.token ='eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6InJldmlld2VyIiwiaWF0IjoxNTE2MjM5MDIyfQ.E2cX6ZJtMO1zhxGhSBQJEk9xvNm1yclHLC9389rnV7U';
    // this.decoded = jwt_decode(this.token);
    // this.r = this.decoded['role'];
  }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }

  loginUser() {
    // this._router.navigateByUrl('/home?token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6InJldmlld2VyIiwiaWF0IjoxNTE2MjM5MDIyfQ.f_r1Qr232o_7ZZrbdBLuvrhXjA2hEiq8jaaPBucSSB0"').then(() => {
    //   window.location.reload();
    // });

    // this.decoded = jwt_decode(this.token);
    // this.r = this.decoded['role'];
    // if(this.r==="reviewer"){
    //   this._router.navigate(['reviewer'])
    // }

    // else if(this.r==="speaker"){
    //   this._router.navigate(['speaker'])
    // }

    // else{
    // this._router.navigate(['']);
    // }
  }

  heWantsSpeaker(flag) {
    this.flag = flag;
    console.log(flag);
  }

  heWantsReviewer(flag) {
    this.flag = flag;
    console.log(flag);
  }
  heWantsConferenceChair(flag) {
    this.flag = flag;
    console.log(flag);
  }

  getRole() {
    if (localStorage.getItem('token') === null) {
      return 'none';
    } else {
      if (jwt_decode(localStorage.getItem('token'))['author'] && localStorage.getItem('role') === 'speaker') {
        return 'speaker';
      } else if (jwt_decode(localStorage.getItem('token'))['isChair']) {
        return 'chair';
      } else if (jwt_decode(localStorage.getItem('token'))['isCommittemember']) {
        return 'reviewer';
      }
      else if(localStorage.getItem('role') === 'participant'){

        return 'participant'
      }
    }
  }

  getName() {
    if (localStorage.getItem('token') === null) {
      return 'none';
    } else {
    return jwt_decode(localStorage.getItem('token'))['name'];
    }
  }

  logoutUser() {
    this.token = '';
    this.decoded = '';
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
    this._router.navigate(['']);
  }

  setToken(tok: string) {
    localStorage.setItem('token', tok);
    this.token = tok;
    localStorage.setItem('email', jwt_decode(localStorage.getItem('token'))['iss']);
    window.location.reload();
  }
}
