import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './modules/login/login.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTabsModule } from '@angular/material/tabs';
import { MatRadioModule } from '@angular/material/radio';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { HomeComponent } from './modules/home/home.component';
import { VideoComponent } from './modules/video/video.component';
import { NgxSpinnerModule } from "ngx-spinner";
import { MatVideoModule } from 'mat-video';
import { MatSelectModule } from '@angular/material/select';
import { ChannelComponent } from './modules/channel/channel.component';
import { MatTableModule } from '@angular/material/table';
import { VideoCardComponent } from './shared/components/video-card/video-card.component';
import { MatMenuModule } from '@angular/material/menu';
import { SignupComponent } from './modules/signup/signup.component';
import { ChartsModule } from 'ng2-charts';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpClientModule } from '@angular/common/http';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatBadgeModule } from '@angular/material/badge';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    HomeComponent,
    VideoComponent,
    ChannelComponent,
    VideoCardComponent,
    SignupComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatRadioModule,
    MatCardModule,
    MatListModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    MatCheckboxModule,
    MatGridListModule,
    MatTabsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    NgxSpinnerModule,
    MatVideoModule,
    MatSelectModule,
    MatTableModule,
    MatMenuModule,
    ChartsModule,
    MatSnackBarModule,
    HttpClientModule,
    MatDatepickerModule,
    MatBadgeModule,
    MatPaginatorModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatSlideToggleModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
