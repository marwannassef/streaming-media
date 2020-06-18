import { Video } from './video.model';

export class Channel {
    id: number;
    channelName: string;
    creationDate: string;
    description: string;
    isActive: boolean;
    channelOwner: string;
    channelImg: string;
    coverImg: string;
    numOfSubscrbiers: number;
    isSubscribed: boolean;
    videos: Video[];
    subscribedChannels: Channel[];

    constructor() {
        this.videos = new Array();
        this.subscribedChannels = new Array();
    }

}