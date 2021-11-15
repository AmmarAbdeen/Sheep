import {Component, OnInit} from '@angular/core';
import {AppMainComponent} from './app.main.component';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[];

    constructor(public app: AppMainComponent) {}

    ngOnInit() {
        this.model = [
            {label: 'لوحة القيادة', icon: 'dashboard', routerLink: ['/sheepframe/home/dashboard']},
            {
                label: ' القائمة الرئيسية للقطيع', icon: 'list', badge: '2', routerLink: ['/sheepframe/home/sheepcomponents'], badgeStyleClass: 'orange-badge',
                items: [
                    {label: 'اضافة (فحل / نعجة) جديد', icon: 'add', routerLink: ['/sheepframe/home/sheepcomponents/addNewSheep']},
                    {label: 'ادارة القطيع', icon: 'search', routerLink: ['/sheepframe/home/sheepcomponents/managesheep']},
                ]
            },
            {
                label: ' القائمة الرئيسية للمواليد', icon: 'list', badge: '2', routerLink: ['/sheepframe/home/lambcomponents'], badgeStyleClass: 'orange-badge',
                items: [
                    {label: 'اضافة حمل جديد', icon: 'add', routerLink: ['/sheepframe/home/lambcomponents/addNewLamb']},
                    {label: 'ادارة المواليد', icon: 'search', routerLink: ['/sheepframe/home/lambcomponents/managelamb']},
                ]
            },
            {
                label: 'الاعلاف', icon: 'list', badge: '3', routerLink: ['/sheepframe/home/feedcomponents'], badgeStyleClass: 'orange-badge',
                items: [
                  {label: 'مشتريات الاعلاف ', icon: 'shopping_cart', routerLink: ['/sheepframe/home/feedcomponents/savefeed']},
                  {label: 'القيمة الغذائية للاعلاف', icon: 'shopping_cart', routerLink: ['/sheepframe/home/feedcomponents/feedlookups']},
                  {label: 'الاعلاف المخزنة', icon: 'shopping_cart', routerLink: ['/sheepframe/home/feedcomponents/storedfeed']}
        ]},
        {
            label: 'الحظائر', icon: 'storefront', badge: '2', routerLink: ['/sheepframe/home/placecomponents'], badgeStyleClass: 'orange-badge',
            items: [
              {label: 'الحظائر', icon: 'storefront', routerLink: ['/sheepframe/home/placecomponents/places']},
              {label: 'تغذية الحظائر', icon: 'shopping_cart', routerLink: ['/sheepframe/home/placecomponents/placesfeed']}
    ]},
    {
        label: 'الادوية', icon: 'storefront', badge: '2', routerLink: ['/sheepframe/home/medicinecomponents'], badgeStyleClass: 'orange-badge',
        items: [
          {label: 'اضافة ادوية', icon: 'storefront', routerLink: ['/sheepframe/home/medicinecomponents/medicine']},
          {label: 'ادوية امراض القطيع', icon: 'shopping_cart', routerLink: ['/sheepframe/home/medicinecomponents/sheepmedicine']}
            ]
        },

        {
            label: 'حركة القطيع', icon: 'storefront', badge: '2', routerLink: ['/sheepframe/home/sheepmovement'], badgeStyleClass: 'orange-badge',
            items: [
              {label: 'حركة القطيع', icon: 'storefront', routerLink: ['/sheepframe/home/sheepmovement/movement']},
              {label: ' بحث ', icon: 'shopping_cart', routerLink: ['/sheepframe/home/sheepmovement/movementsearch']}
                ]
            },

        ];
    }

    onMenuClick() {
        this.app.menuClick = true;
    }
}
