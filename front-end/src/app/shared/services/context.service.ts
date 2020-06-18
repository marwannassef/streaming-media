import { Injectable } from '@angular/core';
import * as moment from "moment";

@Injectable({
  providedIn: 'root'
})
export class ContextService {

  constructor() { }

  getToken() {
    return localStorage.getItem('access_token');
  }

  getUserName() {
    return localStorage.getItem('user_name');
  }

  getUserPhoto() {
    return localStorage.getItem('user_photo');
  }

  getUserId() {
    return localStorage.getItem('user_id');
  }

  public isLoggedIn() {
      return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
      return !this.isLoggedIn();
  }

  isAdmin() {
      return localStorage.getItem('user_role') === 'ROLE_ADMIN';
  }

  getExpiration() {
      const expiration = localStorage.getItem("access_token_expiry");
      const expiresAt = JSON.parse(expiration);
      return moment(expiresAt);
  }   

  logout() {
      localStorage.removeItem("access_token");
      localStorage.removeItem("access_token_expiry");
      localStorage.removeItem("user_role");
      localStorage.removeItem("user_name");
      localStorage.removeItem("user_photo");
      localStorage.removeItem("user_id");
  }

}
