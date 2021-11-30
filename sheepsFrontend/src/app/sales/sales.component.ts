import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Sales } from '../vo/Sales';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {

  form = new FormGroup({
    type: new FormControl('', [Validators.required]),
    code: new FormControl('', [Validators.required]),
    color: new FormControl('', [Validators.required]),
    amount: new FormControl('', [Validators.required]),
    buyer: new FormControl(),
    description: new FormControl()   
});
sales : any[];
types : SelectItem [];
colors : SelectItem [];
cols: any[];
validateAllForm = false;
sale :Sales;
@ViewChild('addForm') saveMedicine: NgForm;
constructor(
  private router: Router,
  private datePipe: DatePipe,
  private route: ActivatedRoute,
  private generalService: GeneralService,
  private messageService: MessageService
) { 
  this.sale = new Sales();
}

ngOnInit(): void {
      this.cols = [
        { field: 'type', header: 'النوع ' },
        { field: 'code', header: 'الكود ' },
        { field: 'color', header: 'اللون ' },
        { field: 'amount', header: 'المبلغ ' },
        { field: 'creationDate', header: 'وقت البيع ' }, 
        { field: 'buyer', header: 'المشتري ' },
        { field: 'description', header: 'الوصف' }
        
    ];
this.getColors();
    this.types = [ 
      {label: ' من القطيع ', value: 'sheep'},
      {label: 'من الاولاد', value: 'lamb'},
      ];

    this.generalService.getSales().subscribe(
      (responseData: any) => {
          this.sales = responseData;
          if (!this.sales.length) {
              this.messageService.clear();
              document.documentElement.scrollTop = 0;
              this.messageService.add({ severity: 'error', detail:"لا يوجد مبيعات  " });
        } else {
              this.messageService.clear();
          }
    },
    (error: any) => {
              document.documentElement.scrollTop = 0;
              this.messageService.clear();
              this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد مبيعات' });
              this.sales = [];
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
        type: this.sale.type,
        amount: this.sale.amount,
        code: this.sale.code,
        color: this.sale.color,
        buyer: this.sale.buyer,
        description: this.sale.description
          };
  this.generalService.saveSale(model).subscribe(
          (response) => {
            document.documentElement.scrollTop = 0;
            this.messageService.clear();
            this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
            this.saveMedicine.reset();
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

getColors(){
  this.colors =[];
    this.generalService.getLookupsByType("color").subscribe(
      (responseData: any) => {
          for (let i = 0; i < responseData.length; i++) {
              this.colors.push({
                  label: responseData[i].nameAR,
                  value: responseData[i].nameAR
              });
          }
      },
      (error: any) => {
        document.documentElement.scrollTop = 0;
        this.messageService.clear();
        console.log(error);
        this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الحظائر حاول مرة اخرى"});
      }
  );
}

}
