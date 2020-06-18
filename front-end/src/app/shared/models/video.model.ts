import { Channel } from './channel.model';

export class Video {
    id: number;
    videoTitle: string;
    isbanned: boolean;
    videoUrl: string;
    description: string;
    publishedDate: string;
    likeNum: number;
    liked: boolean;
    numOfLikes: number;
    numOfViews: number;
    channelNumber: number;
    channel: Channel;
    recommendedVideos: Video[];
    comments: Comment[];

    constructor() {
        this.channel = new Channel();
        this.recommendedVideos = new Array();
        this.comments = new Array();
    }

}