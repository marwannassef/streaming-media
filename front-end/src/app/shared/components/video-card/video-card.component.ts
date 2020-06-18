import { Component, OnInit, Injectable, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Video } from '../../models/video.model';
import { Channel } from '../../models/channel.model';

@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.scss']
})
export class VideoCardComponent implements OnInit {

  @Input() channelLogo = 'https://www.w3schools.com/howto/img_avatar.png';
  @Input() videoSrc = 'https://www.w3schools.com/html/mov_bbb.mp4';
  @Input() videoName = 'Name';
  @Input() videoDetails = 'Views';
  @Input() channelId = '0';
  @Input() video: Video;
  @Input() showChannelLogo = true;

  @Output() channelEventEmitter: EventEmitter<string>;
  @Output() videoEventEmitter: EventEmitter<string>;

  constructor() {
    this.channelEventEmitter = new EventEmitter<string>();
    this.videoEventEmitter = new EventEmitter<string>();
    this.video = new Video();
    this.video.channel = new Channel();
  }

  ngOnInit(): void {
    this.videoSrc = 'https://streaming-video-storage.s3.amazonaws.com/videos/' + this.video.videoUrl;
    if (this.showChannelLogo)
      this.channelLogo = 'https://streaming-video-storage.s3.amazonaws.com/photos/' + this.video.channel.channelImg;
  }

  onChannelClick(value) {
    this.channelEventEmitter.emit(value);
  }

  onVideoClick(value) {
    this.videoEventEmitter.emit(value);
  }

  play() {
    const video = document.getElementById(this.video.id + '') as HTMLVideoElement;
    video.playbackRate = 2;
    video.muted = true;
    video.play();
  }

  pause() {
    const video = document.getElementById(this.video.id + '') as HTMLVideoElement;
    video.pause();
    video.currentTime = 0;
  }

}
