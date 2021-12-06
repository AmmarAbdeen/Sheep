import {Component, OnInit} from '@angular/core';
import {AppMainComponent} from './app.main.component';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[];
    userprivileges: any[];

    constructor(public app: AppMainComponent) {}

    ngOnInit() {
        this.userprivileges = JSON.parse(localStorage.getItem('user')).privileges;
        let items = [];
        this.model = [];
        let index: any;
        let i: any;
        for (index in this.userprivileges) {
            if (this.userprivileges[index].parentPrivilegeId == null) {

                    for (i in this.userprivileges) {
                        if (this.userprivileges[i].parentPrivilegeId != null) {
                            if (this.userprivileges[i].parentPrivilegeId == this.userprivileges[index].id) {
                                items.push({
                                    label: this.userprivileges[i].arabicName,
                                    icon: this.userprivileges[i].iconPath,
                                    routerLink: this.userprivileges[i].routerLink,
                                    privilegeorder: this.userprivileges[i].privilegeorder
                                });
                            }
                        }
                    }
                    if (items.length == 0) {
                        this.model.push({
                            label: this.userprivileges[index].arabicName,
                            icon: this.userprivileges[index].iconPath,
                            badgeStyleClass: 'orange-badge',
                            routerLink: this.userprivileges[index].routerLink,
                            privilegeorder: this.userprivileges[index].privilegeorder
                        });

                    } else {
                        this.model.push({
                            label: this.userprivileges[index].arabicName,
                            icon: this.userprivileges[index].iconPath,
                            badgeStyleClass: 'orange-badge',
                            routerLink: this.userprivileges[index].routerLink,
                            items: items,
                            privilegeorder: this.userprivileges[index].privilegeorder
                        });
                    }

                    items = [];
                
            }
        }
    //     this.model = [
    //         {label: 'لوحة القيادة', icon: 'dashboard', routerLink: ['/home/dashboard']},
    //         {
    //             label: ' القائمة الرئيسية للقطيع', icon: 'list', badge: '2', routerLink: ['/home/sheepcomponents'], badgeStyleClass: 'orange-badge',
    //             items: [
    //                 {label: 'اضافة (فحل / نعجة) جديد', icon: 'add', routerLink: ['/home/sheepcomponents/addNewSheep']},
    //                 {label: 'ادارة القطيع', icon: 'search', routerLink: ['/home/sheepcomponents/managesheep']},
    //             ]
    //         },
    //         {
    //             label: ' القائمة الرئيسية للمواليد', icon: 'list', badge: '2', routerLink: ['/home/lambcomponents'], badgeStyleClass: 'orange-badge',
    //             items: [
    //                 {label: 'اضافة حمل جديد', icon: 'add', routerLink: ['/home/lambcomponents/addNewLamb']},
    //                 {label: 'ادارة المواليد', icon: 'search', routerLink: ['/home/lambcomponents/managelamb']},
    //             ]
    //         },
    //         {
    //             label: 'الاعلاف', icon: 'list', badge: '3', routerLink: ['/home/feedcomponents'], badgeStyleClass: 'orange-badge',
    //             items: [
    //               {label: 'مشتريات الاعلاف ', icon: 'shopping_cart', routerLink: ['/home/feedcomponents/savefeed']},
    //               {label: 'القيمة الغذائية للاعلاف', icon: 'shopping_cart', routerLink: ['/home/feedcomponents/feedlookups']},
    //               {label: 'الاعلاف المخزنة', icon: 'shopping_cart', routerLink: ['/home/feedcomponents/storedfeed']}
    //     ]},
    //     {
    //         label: 'الحظائر', icon: 'storefront', badge: '2', routerLink: ['/home/placecomponents'], badgeStyleClass: 'orange-badge',
    //         items: [
    //           {label: 'الحظائر', icon: 'storefront', routerLink: ['/home/placecomponents/places']},
    //           {label: 'تغذية الحظائر', icon: 'shopping_cart', routerLink: ['/home/placecomponents/placesfeed']}
    // ]},
    // {
    //     label: 'الادوية', icon: 'storefront', badge: '2', routerLink: ['/home/medicinecomponents'], badgeStyleClass: 'orange-badge',
    //     items: [
    //       {label: 'اضافة ادوية', icon: 'storefront', routerLink: ['/home/medicinecomponents/medicine']},
    //       {label: 'ادوية امراض القطيع', icon: 'shopping_cart', routerLink: ['/home/medicinecomponents/sheepmedicine']}
    //         ]
    //     },

    //     {
    //         label: 'حركة القطيع', icon: 'storefront', badge: '2', routerLink: ['/home/sheepmovement'], badgeStyleClass: 'orange-badge',
    //         items: [
    //           {label: 'حركة القطيع', icon: 'storefront', routerLink: ['/home/sheepmovement/movement']},
    //           {label: ' بحث ', icon: 'shopping_cart', routerLink: ['/home/sheepmovement/movementsearch']}
    //             ]
    //         },
    //         {
    //             label: 'المصاريف والدفع', icon: 'attach_money', badge: '3', routerLink: ['/home/payment'], badgeStyleClass: 'orange-badge',
    //             items: [
    //               {label: 'المصاريف العامة', icon: 'attach_money', routerLink: ['/home/payment/generalpayment']},
    //               {label: ' الدخل ', icon: 'attach_money', routerLink: ['/home/payment/income']},
    //               {label: ' المبيعات ', icon: 'attach_money', routerLink: ['/home/payment/sales']}
    //                 ]
    //             },

    //     ];
    }

    onMenuClick() {
        this.app.menuClick = true;
    }
}
