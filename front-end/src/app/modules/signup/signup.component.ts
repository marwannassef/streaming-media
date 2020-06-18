import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user.model';
import { SignupService } from './service/signup.service';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  user: User;
  hidePassword = true;
  hidePasswordVarify = true;
  hide = true;
  profilePhoto = 'http://nicesnippets.com/demo/1499344631_malecostume.png';
  profilePhotoFile: any;
  
  constructor(private router: Router, private signupService: SignupService, private spinner: NgxSpinnerService, private fileUploadService: FileUploadService, private snackBar: MatSnackBar) {
    this.user = new User('', '', '', '');
  }

  ngOnInit(): void {
  }
  
  onSignupClick() {
    this.spinner.show();
    this.signupService.signup(this.user).subscribe(
      response => {
        this.spinner.hide();
        this.snackBar.open('Photo uploaded successfully', 'Dismiss', {
          duration: 2000,
        });
        this.router.navigate(['/login']);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  changeProfilePhoto() {
    document.getElementById('profile-photo').click();
  }

  changeProfileImage(profileImageInput: any) {
    this.profilePhotoFile = profileImageInput.files[0];
    var reader = new FileReader();
    reader.onload = this.handleProfileReaderLoaded.bind(this);
    reader.readAsBinaryString(this.profilePhotoFile);
  }

  handleProfileReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.profilePhoto = 'data:image/png;base64,' + btoa(binaryString);
  }

}
