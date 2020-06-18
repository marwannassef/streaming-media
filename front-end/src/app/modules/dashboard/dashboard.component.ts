import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { DashboardService } from './service/dashboard.service';
import { User } from 'src/app/shared/models/user.model';
import { Video } from 'src/app/shared/models/video.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  displayedUserColumns: string[] = ['name', 'action'];
  dataSourceUser = new MatTableDataSource<User>();
  userLength = 0;

  displayedVideoColumns: string[] = ['name', 'action'];
  dataSourceVideo = new MatTableDataSource<Video>();
  videoLength = 0;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.dashboardService.getUsers(0, 10).subscribe(
      response => {
        this.userLength = response.size;
        this.dataSourceUser = new MatTableDataSource<User>(response.users);
      },
      error => {
        console.log(<any>error);
      }
    );
    this.dashboardService.getVideos(0, 10).subscribe(
      response => {
        this.videoLength = response.size;
        this.dataSourceVideo = new MatTableDataSource<Video>(response.videos);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  onUserPageFired(event){
    this.dashboardService.getUsers(event.pageIndex, event.pageSize).subscribe(
      response => {
        this.userLength = response.size;
        this.dataSourceUser = new MatTableDataSource<User>(response.users);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  onVideoPageFired(event){
    this.dashboardService.getVideos(event.pageIndex, event.pageSize).subscribe(
      response => {
        this.videoLength = response.size;
        this.dataSourceVideo = new MatTableDataSource<Video>(response.videos);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  banUser(user) {
    this.dashboardService.banUser(user.id, !user.banned).subscribe(
      response => {
        user.banned = !user.banned;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  banVideo(video: Video) {
    this.dashboardService.banUser(video.id, !video.isbanned).subscribe(
      response => {
        video.isbanned = !video.isbanned;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

}
