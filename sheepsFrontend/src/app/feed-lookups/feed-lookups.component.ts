import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';
import { FeedLookups } from '../vo/FeedLookups';

@Component({
  selector: 'app-feed-lookups',
  templateUrl: './feed-lookups.component.html',
  styleUrls: ['./feed-lookups.component.css']
})
export class FeedLookupsComponent implements OnInit {

  form = new FormGroup({
    name: new FormControl('', [Validators.required]),
    benefitOfIt: new FormControl('', [Validators.required]),
    dryAmount: new FormControl('', [Validators.required]),
    energyMj: new FormControl('', [Validators.required]),
    calcium: new FormControl('', [Validators.required]),
    phosphorous: new FormControl('', [Validators.required]),
    protein: new FormControl('', [Validators.required])
   
});

cols: any[];
lookups: any[];
feedLookups : FeedLookups;
validateAllForm = false;
@ViewChild('addForm') foodLookups: NgForm;
constructor(
  private router: Router,
  private route: ActivatedRoute,
  private generalService: GeneralService,
  private messageService: MessageService
) {
  this.feedLookups = new FeedLookups();
 }

  ngOnInit(): void {
    this.cols = [
      { field: 'name', header: 'الاعلاف' },
      { field: 'benefitOfIt', header: 'المستفاد منه ' },
      { field: 'dryAmount', header: 'المقدار الجاف' },  
      { field: 'energyMj', header: 'الطاقة' },  
      { field: 'calcium', header: 'الكالسيوم ' },
      { field: 'phosphorous', header: 'الفسفور' },  
      { field: 'protein', header: 'البروتين' },  
      { field: 'edit', header: 'تعديل' }
  ];

  this.generalService.getFeedLookups().subscribe(
    (responseData: any) => {
        this.lookups = responseData;
        if (!this.lookups.length) {
            this.messageService.clear();
            document.documentElement.scrollTop = 0;
            this.messageService.add({ severity: 'error', detail:"لا يوجد قيمة غذائية مخزنة  " });
        } else {
            this.messageService.clear();
        }
    },
    (error: any) => {
            document.documentElement.scrollTop = 0;
            this.messageService.clear();
            this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد قيمة غذائية مخزنة' });
            this.lookups = [];
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
      id : this.feedLookups.id,
      name: this.feedLookups.name,
      benefitOfIt: this.feedLookups.benefitOfIt,
      dryAmount: this.feedLookups.dryAmount,
      energyMj: this.feedLookups.energyMj,
      calcium: this.feedLookups.calcium,
      phosphorous: this.feedLookups.phosphorous,
      protein: this.feedLookups.protein,
            };
    this.generalService.saveFeedLookups(model).subscribe(
                (response) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
                  this.foodLookups.reset();
                 },(error) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  console.log(error);
                  this.messageService.add({severity: 'error', detail: "هناك خطأ في الاضافة حاول مره اخرى  "});
                 }
             );
  }
  }

  edit(rowData: any) {
    document.documentElement.scrollTop = 0;
    this.feedLookups.id= rowData.id;
    this.feedLookups.name = rowData.name;
    this.feedLookups.benefitOfIt = rowData.benefitOfIt;
    this.feedLookups.dryAmount = rowData.dryAmount;
    this.feedLookups.energyMj = rowData.energyMj;
    this.feedLookups.calcium = rowData.calcium;
    this.feedLookups.phosphorous = rowData.phosphorous;
    this.feedLookups.protein = rowData.protein;
  }

  cancel() {
    this.router.navigate(['/home/dashboard']);
  }


}
