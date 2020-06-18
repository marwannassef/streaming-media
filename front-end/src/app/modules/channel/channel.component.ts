import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { Video } from 'src/app/shared/models/video.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ChannelService } from './service/channel.service';
import { Channel } from 'src/app/shared/models/channel.model';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrls: ['./channel.component.scss']
})
export class ChannelComponent implements OnInit {

  displayedColumns: string[] = ['logo', 'name', 'action'];
  videos: Video[];
  video: Video;
  uploadedVideo: any;
  channel: Channel;
  channelId = 0;
  videoSrc = '';
  isUserChannel = true;
  myControl = new FormControl();
  filteredOptions: Observable<Video[]>;
  videoChart;

  profilePhoto = 'https://www.w3schools.com/howto/img_avatar.png';
  coverPhoto = 'http://nicesnippets.com/demo/Nature-Night-Sky-Stars-Blurred-Light-Show-Mountains-WallpapersByte-com-1920x1080.jpg';

  constructor(private router: Router, private route: ActivatedRoute, private channelService: ChannelService, private fileUploadService: FileUploadService, private spinner: NgxSpinnerService, private snackBar: MatSnackBar) {
    this.video = new Video();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.channelId = Number(params.id);
      if (this.channelId === 0) {
        this.channelService.getUserChannel().subscribe(
          response => {
            this.isUserChannel = true;
            this.channel = response;
            this.videos = this.channel.videos;
            this.creationDate = this.channel.creationDate;
            this.profilePhoto = 'https://streaming-video-storage.s3.amazonaws.com/photos/' + this.channel.channelImg;
            this.coverPhoto = 'https://streaming-video-storage.s3.amazonaws.com/covers/' + this.channel.coverImg;
            this.channelService.channelStatistics().subscribe(
              response => {
                this.lineChartData = new Array();
                this.lineChartLabels = new Array();
                let value = new Array();
                value.push(0);
                this.lineChartLabels.push(this.channel.creationDate);
                for (let i = 0; i < response.length; i++) {
                  this.lineChartLabels.push(response[i].date);
                  value.push(response[i].number);
                }
                this.lineChartData.push({ data: value, label: 'Subscription' });
              },
              error => {
                console.log(<any>error);
              }
            );
          },
          error => {
            console.log(<any>error);
          }
        );
      } else {
        this.isUserChannel = false;
        this.channelService.getChannel(this.channelId).subscribe(
          response => {
            this.channel = response;
            this.videos = this.channel.videos;
            this.profilePhoto = 'https://streaming-video-storage.s3.amazonaws.com/photos/' + this.channel.channelImg;
            this.coverPhoto = 'https://streaming-video-storage.s3.amazonaws.com/covers/' + this.channel.coverImg;
          },
          error => {
            console.log(<any>error);
          }
        );
      }
      this.filteredOptions = this.myControl.valueChanges
        .pipe(
          startWith(''),
          map(value => this.filter(value))
        );
    });
  }

  updateVideoChart(ob) {
    this.channelService.videoStatistics(ob.value.id).subscribe(
      response => {
        this.lineChartVideoData = new Array();
        this.lineChartVideoLabels = new Array();
        let value = new Array();
        value.push(0);
        this.lineChartVideoLabels.push(this.channel.creationDate);
        for (let i = 0; i < response.length; i++) {
          this.lineChartVideoLabels.push(response[i].date);
          value.push(response[i].number);
        }
        this.lineChartVideoData.push({ data: value, label: 'Views' });
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  private filter(value: string): Video[] {
    const filterValue = value.toLowerCase();

    return this.videos.filter(vid => vid.videoTitle.toLowerCase().includes(filterValue));
  }

  unsubscribeChannel(channelId: number) {
    this.channelService.unsubscribeChannel(channelId).subscribe(
      response => {
        this.channel.subscribedChannels = this.channel.subscribedChannels.filter(chan => chan.id !== channelId);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  onVideoClick(id: number) {
    this.router.navigate(['/video']);
  }

  onChannelClick(id: number) {
    this.router.navigate(['/channel']);
  }

  changeProfilePhoto() {
    if (this.channelId !== 0) return;
    document.getElementById('profile-photo').click();
  }

  changeProfileImage(profileImageInput: any) {
    this.spinner.show();
    var file = profileImageInput.files[0];
    this.fileUploadService.uploadPhoto(file, this.channel.id).then(data => {
      var reader = new FileReader();
      reader.onload = this.handleProfileReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
      this.spinner.hide();
      this.snackBar.open('Photo uploaded successfully', 'Dismiss', {
        duration: 2000,
      });
    }).catch(error => {
      console.log(<any>error);
      this.spinner.hide();
      this.snackBar.open('Something went wrong..', 'Dismiss', {
        duration: 2000,
      });
    });
  }

  handleProfileReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.profilePhoto = 'data:image/png;base64,' + btoa(binaryString);
  }

  changeCoverPhoto() {
    if (this.channelId !== 0) return;
    document.getElementById('cover-photo').click();
  }

  changeCoverImage(coverImageInput: any) {
    var file = coverImageInput.files[0];
    this.spinner.show();
    this.fileUploadService.uploadCover(file, this.channel.id).then(data => {
      var reader = new FileReader();
      reader.onload = this.handleCoverReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
      this.spinner.hide();
      this.snackBar.open('Cover uploaded successfully', 'Dismiss', {
        duration: 2000,
      });
    }).catch(error => {
      console.log(<any>error);
      this.spinner.hide();
      this.snackBar.open('Something went wrong..', 'Dismiss', {
        duration: 2000,
      });
    });
  }

  handleCoverReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.coverPhoto = 'data:image/png;base64,' + btoa(binaryString);
  }

  openUploadVideo() {
    document.getElementById('upload-video').click();
  }

  uploadVideo(uploadVideoInput: any) {
    this.uploadedVideo = uploadVideoInput.files[0];
    var reader = new FileReader();
    reader.onload = this.handleVideoReaderLoaded.bind(this);
    reader.readAsBinaryString(this.uploadedVideo);
  }

  handleVideoReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.videoSrc = 'data:' + this.uploadedVideo.type + ';base64,' + btoa(binaryString);
  }

  upload() {
    this.spinner.show();
    this.channelService.upload(this.video).subscribe(
      response => {
        this.fileUploadService.uploadVideo(this.uploadedVideo, response.id).then(data => {
          this.channelService.convertVideo(response.id + '.' + (this.uploadedVideo.type + '').split('/')[1]).subscribe(
            response => {
            },
            error => {
              console.log(<any>error);
            }
          );
          this.cancel();
          this.spinner.hide();
          this.snackBar.open('Video uploaded successfully', 'Dismiss', {
            duration: 2000,
          });
        }).catch(error => {
          console.log(<any>error);
          this.cancel();
          this.spinner.hide();
          this.snackBar.open('Something went wrong..', 'Dismiss', {
            duration: 2000,
          });
        });
      },
      error => {
        console.log(<any>error);
        this.cancel();
        this.spinner.hide();
        this.snackBar.open('Something went wrong..', 'Dismiss', {
          duration: 2000,
        });
      }
    );
  }

  cancel() {
    this.videoSrc = '';
  }

  public lineChartData: ChartDataSets[] = [
    { data: [0], label: 'Subscription' }
  ];

  public lineChartVideoData: ChartDataSets[] = [
    { data: [0], label: 'Views' }
  ];

  creationDate = '';

  public lineChartLabels: Label[] = [''];
  public lineChartVideoLabels: Label[] = [''];
  public lineChartOptions: (ChartOptions & { annotation: any }) = {
    responsive: true,
    scales: {
      xAxes: [{}],
      yAxes: [
        {
          id: 'y-axis-0',
          position: 'left',
        }
      ]
    },
    annotation: {
      annotations: [
        {
          type: 'line',
          mode: 'vertical',
          scaleID: 'x-axis-0',
          value: 'March',
          borderColor: 'orange',
          borderWidth: 2,
          label: {
            enabled: true,
            fontColor: 'orange',
            content: 'LineAnno'
          }
        },
      ],
    },
  };
  public lineChartColors: Color[] = [
    {
      backgroundColor: 'rgba(255,0,0,0.3)',
      borderColor: 'red',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';

}
