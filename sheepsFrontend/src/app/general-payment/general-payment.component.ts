import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';
import { GeneralPayment } from '../vo/GeneralPayment';

@Component({
  selector: 'app-general-payment',
  templateUrl: './general-payment.component.html',
  styleUrls: ['./general-payment.component.css']
})
export class GeneralPaymentComponent implements OnInit {
    form = new FormGroup({
      named: new FormControl('', [Validators.required]),
      amount: new FormControl('', [Validators.required]),
      notes: new FormControl(),
      description: new FormControl()   
  });
  payments : any[];
  cols: any[];
  validateAllForm = false;
  generalpayment :GeneralPayment;
  @ViewChild('addForm') saveMedicine: NgForm;
  constructor(
    private router: Router,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService
  ) { 
    this.generalpayment = new GeneralPayment();
  }

  ngOnInit(): void {
        this.cols = [
          { field: 'named', header: 'المسمى ' },
          { field: 'amount', header: 'المبلغ المدفوع' },
          { field: 'creationDate', header: 'وقت الدفع ' }, 
          { field: 'description', header: 'الوصف' },
          { field: 'notes', header: 'الملحوظات ' }
          
      ];

      this.generalService.getPayments().subscribe(
        (responseData: any) => {
            this.payments = responseData;
            if (!this.payments.length) {
                this.messageService.clear();
                document.documentElement.scrollTop = 0;
                this.messageService.add({ severity: 'error', detail:"لا يوجد مصاريف عامة  " });
          } else {
                this.messageService.clear();
            }
      },
      (error: any) => {
                document.documentElement.scrollTop = 0;
                this.messageService.clear();
                this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد أدوية' });
                this.payments = [];
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
          named: this.generalpayment.named,
          amount: this.generalpayment.amount,
          notes: this.generalpayment.notes,
          description: this.generalpayment.description
            };
    this.generalService.savePayment(model).subscribe(
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
