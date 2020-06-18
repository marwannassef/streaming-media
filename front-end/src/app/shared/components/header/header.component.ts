import { Component, OnInit, Injectable, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ContextService } from 'src/app/shared/services/context.service';


@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {

  @Input() dispButton = false;
  @Input() channelLogo = 'https://www.w3schools.com/howto/img_avatar.png';
  @Input() dispLoginButton = false;
  @Input() dispLogoutButton = false;
  @Input() dispUsersButton = false;

  @Input() dispButtonImg: string;

  @Output() onButtonClick: EventEmitter<string>;
  @Output() onLogoutButtonClick: EventEmitter<string>;

  userLoggedIn = false;

  constructor(private router: Router, public contextService: ContextService) {
    this.onButtonClick = new EventEmitter<string>();
    this.channelLogo = 'https://streaming-video-storage.s3.amazonaws.com/photos/' + contextService.getUserPhoto();
  }

  ngOnInit() {
    this.userLoggedIn = this.contextService.isLoggedIn();
  }

  callButton() {
    this.onButtonClick.emit();
  }

  onLoginClick() {
    this.router.navigate(['/login']);
  }

  onLogoClick() {
    this.router.navigate(['/home']);
  }

  onSignupClick() {
    this.router.navigate(['/signup']);
  }

  onMyChannelClick() {
    this.router.navigate(['/channel', 0]);
  }

  onLogoutClick() {
    this.contextService.logout();
    this.router.navigate(['/home']);
  }

  onDashboardClick() {
    this.router.navigate(['/dashboard']);
  }

}
