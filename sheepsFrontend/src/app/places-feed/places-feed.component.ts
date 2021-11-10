import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';
import { PlacesFeed } from '../vo/PlacesFeed';

@Component({
  selector: 'app-places-feed',
  templateUrl: './places-feed.component.html',
  styleUrls: ['./places-feed.component.css']
})
export class PlacesFeedComponent implements OnInit {

  form = new FormGroup({
    place: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]),
    amount: new FormControl('', [Validators.required]),
    feed: new FormControl('', [Validators.required])
   
});
cols: any[];
placesFeed: any[];
places: SelectItem[];
feeds: SelectItem[];
food : PlacesFeed;
validateAllForm = false;
@ViewChild('addForm') savePlacesFeed: NgForm;
  constructor(
    private router: Router,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService
  ) {
    this.food = new PlacesFeed();
   }

  ngOnInit(): void {

    this.cols = [
      { field: 'place', header: 'الحظيرة' },
      { field: 'date', header: 'التاريخ ' },
      { field: 'amount', header: 'الكمية' },  
      { field: 'feed', header: 'العلف' }
      ];
   this.getFeed();
   this.getPlaces();

  this.generalService.getPlacesFeed().subscribe(
    (responseData: any) => {
        this.placesFeed = responseData;
        if (!this.placesFeed.length) {
            this.messageService.clear();
            document.documentElement.scrollTop = 0;
            this.messageService.add({ severity: 'error', detail:"لا يوجد تغذية حظائر تمت من قبل" });
        } else {
            this.messageService.clear();
        }
    },
    (error: any) => {
            document.documentElement.scrollTop = 0;
            this.messageService.clear();
            this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد تغذية حظائر تمت  من قبل' });
            this.placesFeed = [];
        }
    );
  }

  onsubmit(){
    if (this.form.invalid) {
      this.validateAllForm = true;
      this.form.setErrors({ ...this.form.errors, yourErrorName: true });
      document.documentElement.scrollTop = 0;
      this.messageService.clear();
      this.messageService.add({ severity: 'error', detail: "من فضلك ادخل الحقول المطلوبة" });
      return;
     }else  {
    const model = {
      place: this.food.place,
      date: this.datePipe.transform(this.food.date, 'yyyy-MM-dd 00:00'),
      amount: this.food.amount,
      feed: this.food.feed
            };
    this.generalService.savePlacesFeed(model).subscribe(
                (response) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
                  this.savePlacesFeed.reset();
                 },(error) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  console.log(error);
                  this.messageService.add({severity: 'error', detail: "هناك خطأ في الاضافة حاول مره اخرى  "});
                 }
             );
  }
  }

  getFeed(){
    this.feeds = [];
    this.generalService.getStoredFeed().subscribe(
        (responseData: any) => {
            for (let i = 0; i < responseData.length; i++) {
                this.feeds.push({
                    label: responseData[i].name,
                    value: responseData[i]
                });
            }
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الاعلاف حاول مرة اخرى"});
        }
    );
  }

  getPlaces(){
    this.places = [];
    this.generalService.getPlaces().subscribe(
        (responseData: any) => {
            for (let i = 0; i < responseData.length; i++) {
                this.places.push({
                    label: responseData[i].code,
                    value: responseData[i]
                });
            }console.log( this.places);
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الحظائر حاول مرة اخرى"});
        }
    );
  }

  cancel() {
    this.router.navigate(['/sheepframe/home/dashboard']);
  }


}
