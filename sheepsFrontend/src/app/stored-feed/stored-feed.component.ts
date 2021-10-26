import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Feed } from '../vo/feed';

@Component({
  selector: 'app-stored-feed',
  templateUrl: './stored-feed.component.html',
  styleUrls: ['./stored-feed.component.css']
})
export class StoredFeedComponent implements OnInit {

cols: any[];
food: any[];
constructor(
  private router: Router,
  private datePipe: DatePipe,
  private route: ActivatedRoute,
  private generalService: GeneralService,
  private messageService: MessageService
) { }

  ngOnInit(): void {

    this.cols = [
      { field: 'name', header: 'الاعلاف' },
      { field: 'weight', header: 'الوزن' },  
      { field: 'quantity', header: 'الكمية' }
  ];

  this.generalService.getStoredFeed().subscribe(
    (responseData: any) => {
        this.food = responseData;
        if (!this.food.length) {
            this.messageService.clear();
            document.documentElement.scrollTop = 0;
            this.messageService.add({ severity: 'error', detail:"لا يوجد اعلاف مخزنة" });
        } else {
            this.messageService.clear();
        }
    },
    (error: any) => {
            document.documentElement.scrollTop = 0;
            this.messageService.clear();
            this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد اعلاف مخزنة' });
            this.food = [];
        }
);
  }

}
