import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Feed } from '../vo/feed';

@Component({
  selector: 'app-save-feed',
  templateUrl: './save-feed.component.html',
  styleUrls: ['./save-feed.component.css']
})
export class SaveFeedComponent implements OnInit {

  form = new FormGroup({
    name: new FormControl('', [Validators.required]),
    storageDate: new FormControl('', [Validators.required]),
    weight: new FormControl('', [Validators.required]),
    quantity: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required])
   
});
cols: any[];
food: any[];
feed : Feed;
validateAllForm = false;
@ViewChild('addForm') saveFeed: NgForm;
  constructor(
    private router: Router,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService
  ) {
    this.feed = new Feed();
   }

  ngOnInit(): void {
      this.cols = [
        { field: 'name', header: 'الاعلاف' },
        { field: 'storageDate', header: 'تاريخ الشراء' },
        { field: 'weight', header: 'الوزن' },  
        { field: 'quantity', header: 'الكمية' }, 
        { field: 'price', header: 'السعر' },  
    ];

    this.generalService.getFeed().subscribe(
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
      name: this.feed.name,
      storageDate: this.datePipe.transform(this.feed.storageDate, 'yyyy-MM-dd 00:00'),
      weight: this.feed.weight,
      quantity: this.feed.quantity,
      price: this.feed.price
            };
    this.generalService.saveFeed(model).subscribe(
                (response) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
                  this.saveFeed.reset();
                 },(error) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  console.log(error);
                  this.messageService.add({severity: 'error', detail: "هناك خطأ في الاضافة حاول مره اخرى  "});
                 }
             );
  }
  }

  cancel() {
    this.router.navigate(['/home/dashboard']);
  }

}
