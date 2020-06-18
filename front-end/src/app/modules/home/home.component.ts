import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HomeService } from './services/home.service';
import { Video } from 'src/app/shared/models/video.model';
import { ContextService } from 'src/app/shared/services/context.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  videos: Video[];

  constructor(private router: Router, private homeService: HomeService, private contextService: ContextService) {
  }

  ngOnInit(): void {
    this.homeService.readVideos().subscribe(
      response => {
        this.videos = response;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  onVideoClick(id: number) {
    this.router.navigate(['/video', id]);
  }

  onChannelClick(id: number) {
    this.router.navigate(['/channel', id]);
  }

}
