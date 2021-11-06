import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';

@Component({
  selector: 'app-sheep-movement-search',
  templateUrl: './sheep-movement-search.component.html',
  styleUrls: ['./sheep-movement-search.component.css']
})
export class SheepMovementSearchComponent implements OnInit {

  form = new FormGroup({
    fromDate: new FormControl(),
    toDate: new FormControl(),
    sheep: new FormControl(),
    lamb: new FormControl(),

});
cols: any[];
sheepMovements: any[];
@ViewChild('searchForm') searchForm: NgForm;
lambs: SelectItem[];
sheeps: SelectItem[];
constructor(
private router: Router,
private generalService: GeneralService,
private messageService: MessageService,
private datePipe: DatePipe) {
}

  ngOnInit(): void {
    this.getLambs();
    this.getSheeps();
    this.cols = [
      { field: 'date', header: 'تاريخ الدخول' },
      { field: 'outDate', header: 'تاريخ الخروج' },
      { field: 'notes', header: 'الملحوظات' },  
      { field: 'description', header: 'الوصف' },  
      { field: 'place', header: 'المكان' },  
      { field: 'sheep', header: 'القطيع' },  
      { field: 'lamb', header: 'الحمل' }
  ];
  }

  onsubmit(){
    const body = {
      sheep:  null,
      lamb: null,
      fromDate:  null,
      toDate: null
  };


  if (this.searchForm.value.toDate !== '') {
    body.toDate = this.datePipe.transform(this.searchForm.value.toDate, 'yyyy-MM-dd 23:59');
    }
  if (this.searchForm.value.fromDate !== '') {
    body.fromDate = this.datePipe.transform(this.searchForm.value.fromDate, 'yyyy-MM-dd 00:00');
   }
  if (this.searchForm.value.sheep !== '') {
    body.sheep = this.searchForm.value.sheep;
  }
  if (this.searchForm.value.lamb !== '') {
    body.lamb = this.searchForm.value.lamb;
  }

this.generalService.getSheepMovements(body).subscribe(
  (responseData: any) => {
      this.sheepMovements = responseData;
      if (!this.sheepMovements.length) {
          this.messageService.clear();
          document.documentElement.scrollTop = 0;
          this.messageService.add({ severity: 'error', detail:"لا يوجد حركات لهذا القطيع " });
      } else {
        console.log(responseData);
          this.messageService.clear();
      }
  },
  (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد حركات' });
          this.sheepMovements = [];
      }
);
}

  getLambs() {
    this.lambs = [];
    const body = { };
    this.generalService.getLambs(body).subscribe(
        (responseData: any) => {
          console.log(responseData);
            for (let i = 0; i < responseData.length; i++) {
                this.lambs.push({
                    label: "code : "+responseData[i].code +" , color : "+responseData[i].color,
                    value: responseData[i]
                });
            }
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل المواليد حاول مرة اخرى"});
        }
    );
  }

  getSheeps() {
    this.sheeps = [];
    this.generalService.getAllSheeps().subscribe(
        (responseData: any) => {
            for (let i = 0; i < responseData.length; i++) {
                this.sheeps.push({
                    label: "code : "+responseData[i].code +" , color : "+responseData[i].color,
                    value: responseData[i]
                });
            }
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل القطيع حاول مرة اخرى"});
        }
    );
  }
  
  resetData() {
    this.searchForm.reset();
    this.sheepMovements = [];
    this.messageService.clear();
  
  
  }
}
