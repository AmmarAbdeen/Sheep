import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Income } from '../vo/Income';

@Component({
  selector: 'app-income',
  templateUrl: './income.component.html',
  styleUrls: ['./income.component.css']
})
export class IncomeComponent implements OnInit {

    form = new FormGroup({
      source: new FormControl('', [Validators.required]),
      amount: new FormControl('', [Validators.required]),
      description: new FormControl()   
  });
    incomes : any[];
    cols: any[];
    validateAllForm = false;
    income :Income;
    @ViewChild('addForm') saveMedicine: NgForm;
constructor(
  private router: Router,
  private datePipe: DatePipe,
  private route: ActivatedRoute,
  private generalService: GeneralService,
  private messageService: MessageService
) { 
  this.income = new Income();
}

ngOnInit(): void {
      this.cols = [
        { field: 'source', header: 'المصدر ' },
        { field: 'amount', header: 'المبلغ المدفوع' },
        { field: 'creationDate', header: 'وقت الدفع ' }, 
        { field: 'description', header: 'الوصف' }
        
    ];

    this.generalService.getIncomes().subscribe(
      (responseData: any) => {
          this.incomes = responseData;
          if (!this.incomes.length) {
              this.messageService.clear();
              document.documentElement.scrollTop = 0;
              this.messageService.add({ severity: 'error', detail:"لا يوجد أدوية  " });
        } else {
              this.messageService.clear();
          }
    },
    (error: any) => {
              document.documentElement.scrollTop = 0;
              this.messageService.clear();
              this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد دخل' });
              this.incomes = [];
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
          source: this.income.source,
          amount: this.income.amount,
          description: this.income.description
            };
    this.generalService.saveIncome(model).subscribe(
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

}
