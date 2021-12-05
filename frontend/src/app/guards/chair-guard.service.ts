import { Injectable } from '@angular/core';
import { AuthService } from '../AuthentificationService/auth.service';
import { Router, CanActivate } from '@angular/router';

@Injectable()
export class ChairGuardService implements CanActivate {

  constructor(private _authService: AuthService,
    private _router: Router) { }

  canActivate(): boolean {
    if (this._authService.isLoggedIn() && this._authService.getRole() === "chair") {
      console.log('true')
      return true
    } else {
      console.log('false')
      this._router.navigate(['home'])
      return false
    }
  }
}
