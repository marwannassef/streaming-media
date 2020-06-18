import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user.model';
import { Router } from '@angular/router';
import { LoginService } from './service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user: User;

  constructor(private router: Router, private loginService: LoginService) {
    this.user = new User('', '', '', ''); 
  }

  ngOnInit(): void {
  }

  onRegisterClick() {
  }

  onLoginClick() {
    this.loginService.login(this.user).subscribe(
      response => {
        this.loginService.setSession(response);
        this.router.navigate(['/home']);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

}
