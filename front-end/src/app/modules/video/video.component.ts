import { Component, OnInit, ViewChild, Renderer2, AfterViewInit, OnDestroy } from '@angular/core';
import videojs from 'video.js';
import { VideoService } from './service/video.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Video } from 'src/app/shared/models/video.model';
import { Comment } from 'src/app/shared/models/comment.model';
import { ContextService } from 'src/app/shared/services/context.service';

declare const chrome: any
declare var require: any;
require('videojs-contrib-quality-levels');
require('videojs-hls-quality-selector');
require('videojs-seek-buttons');
require("@silvermine/videojs-chromecast")(videojs, {
  preloadWebComponents: true
});
require('videojs-share');
require('videojs-logo');

interface Food {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.scss']
})
export class VideoComponent implements OnInit, AfterViewInit, OnDestroy {

  selectedValue: string = 'pizza-1';
  selectedPrevValue: string = 'pizza-1';
  channelLogo = 'https://www.w3schools.com/howto/img_avatar.png';

  foods: Food[] = [
    { value: 'steak-0', viewValue: 'Steak' },
    { value: 'pizza-1', viewValue: 'Pizza' },
    { value: 'tacos-2', viewValue: 'Tacos' }
  ];

  public player: videojs.Player;

  video: Video;
  videoId = 0;
  comment: string;

  url = 'https://streaming-video-storage.s3.amazonaws.com/convertedVideos/';

  src = 'https://streaming-video-storage.s3.amazonaws.com/test/file_example_MP4_480_1_5MG.mp4';

  constructor(private renderer: Renderer2, private router: Router, private videoService: VideoService, private route: ActivatedRoute, public contextService: ContextService) {
    this.video = new Video();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.videoId = params.id;
      this.videoService.getVideo(this.videoId).subscribe(
        response => {
          this.video = response;
          this.channelLogo = 'https://streaming-video-storage.s3.amazonaws.com/photos/' + this.video.channel.channelImg;
          var player = videojs(document.querySelector('.video-js'));
          player.src({
                src: this.url + this.videoId + '/output.m3u8',
                type: 'application/x-mpegURL'
          });          
        },
        error => {
          console.log(<any>error);
        }
      );
    });
  }

  ngAfterViewInit() {
    const options = {
      'sources': [{
        'src': this.url + this.videoId + '/output.m3u8',
        'type': 'application/x-mpegURL'
      }
      ],
    };
    this.player = videojs('my-video', options, function onPlayerReady() {
      var myPlayer = this, id = myPlayer.id();
      myPlayer.hlsQualitySelector();
    });

    this.player.seekButtons({
      forward: 15,
      back: 15
    });

    var shareOptions = {
      socials: ['fb', 'tw', 'reddit', 'gp', 'messenger', 'linkedin', 'telegram', 'whatsapp', 'viber', 'vk', 'ok', 'mail'],
     
      url: window.location.href,
      title: 'videojs-share',
      description: 'video.js share plugin',
      image: 'https://dummyimage.com/1200x630',
     
      fbAppId: '12345',
      redirectUri: window.location.href + '#close',
     
      isVkParse: true,
    }    

    this.player.share(shareOptions);
  }

  ngOnDestroy(): void {
    if (this.player != null) {
      this.player.dispose();
    }
  }

  onVideoClick(id: number) {
    this.router.navigate(['/video', id]);
  }

  onChannelClick(id: number) {
    this.router.navigate(['/channel', id]);
  }

  onSubscribeClick() {
    this.videoService.subscribeChannel(this.video.channel.id).subscribe(
      response => {
        this.video.channel.isSubscribed = ! this.video.channel.isSubscribed;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  onUnsubscribeClick() {
    this.videoService.unsubscribeChannel(this.video.channel.id).subscribe(
      response => {
        this.video.channel.isSubscribed = ! this.video.channel.isSubscribed;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  onCommentClick() {
    this.videoService.leaveComment(this.videoId, this.comment).subscribe(
      response => {
        this.comment = '';
        if (this.video.comments === undefined)
          this.video.comments = new Array();
        this.video.comments.push(response);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  onLikeClick() {
    if (this.video.liked) {
      this.videoService.unlikeVideo(this.videoId).subscribe(
        response => {
          this.video.liked = !this.video.liked;
          this.video.numOfLikes--;
        },
        error => {
          console.log(<any>error);
        }
      );
    } else {
      this.videoService.likeVideo(this.videoId).subscribe(
        response => {
          this.video.liked = !this.video.liked;
          this.video.numOfLikes++;
        },
        error => {
          console.log(<any>error);
        }
      );
    }
  }

}
