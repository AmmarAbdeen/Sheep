import { DoCheck, ElementRef, EventEmitter, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Color } from './color';
import { ThemeService } from './theme.service';
import { ChartConfiguration, ChartDataSets, ChartOptions, ChartPoint, ChartType, PluginServiceGlobalRegistration, PluginServiceRegistrationOptions } from 'chart.js';
import * as ɵngcc0 from '@angular/core';
export declare type SingleDataSet = Array<number | null | undefined | number[]> | ChartPoint[];
export declare type MultiDataSet = SingleDataSet[];
export declare type SingleOrMultiDataSet = SingleDataSet | MultiDataSet;
export declare type PluginServiceGlobalRegistrationAndOptions = PluginServiceGlobalRegistration & PluginServiceRegistrationOptions;
export declare type SingleLineLabel = string;
export declare type MultiLineLabel = string[];
export declare type Label = SingleLineLabel | MultiLineLabel;
export declare class BaseChartDirective implements OnChanges, OnInit, OnDestroy, DoCheck {
    private element;
    private themeService;
    data: SingleOrMultiDataSet;
    datasets: ChartDataSets[];
    labels: Label[];
    options: ChartOptions;
    chartType: ChartType;
    colors: Color[];
    legend: boolean;
    plugins: PluginServiceGlobalRegistrationAndOptions[];
    chartClick: EventEmitter<{
        event?: MouseEvent;
        active?: {}[];
    }>;
    chartHover: EventEmitter<{
        event: MouseEvent;
        active: {}[];
    }>;
    ctx: string;
    chart: Chart;
    private old;
    private subs;
    /**
     * Register a plugin.
     */
    static registerPlugin(plugin: PluginServiceGlobalRegistrationAndOptions): void;
    static unregisterPlugin(plugin: PluginServiceGlobalRegistrationAndOptions): void;
    constructor(element: ElementRef, themeService: ThemeService);
    ngOnInit(): void;
    private themeChanged;
    ngDoCheck(): void;
    copyLabel(a: Label): Label;
    labelsEqual(a: Label, b: Label): boolean;
    copyColor(a: Color): Color;
    colorsEqual(a: Color, b: Color): boolean;
    updateColors(): void;
    ngOnChanges(changes: SimpleChanges): void;
    ngOnDestroy(): void;
    update(duration?: any): {};
    hideDataset(index: number, hidden: boolean): void;
    isDatasetHidden(index: number): boolean;
    toBase64Image(): string;
    getChartConfiguration(): ChartConfiguration;
    getChartBuilder(ctx: string): Chart;
    smartMerge(options: any, overrides: any, level?: number): any;
    private isMultiLineLabel;
    private joinLabel;
    private propagateDatasetsToData;
    private propagateDataToDatasets;
    private isMultiDataSet;
    private getDatasets;
    private refresh;
    static ɵfac: ɵngcc0.ɵɵFactoryDef<BaseChartDirective>;
    static ɵdir: ɵngcc0.ɵɵDirectiveDefWithMeta<BaseChartDirective, "canvas[baseChart]", ["base-chart"], {
    "options": "options";
    "data": "data";
    "datasets": "datasets";
    "labels": "labels";
    "chartType": "chartType";
    "colors": "colors";
    "legend": "legend";
    "plugins": "plugins";
}, {
    "chartClick": "chartClick";
    "chartHover": "chartHover";
}, never>;
}

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYmFzZS1jaGFydC5kaXJlY3RpdmUuZC50cyIsInNvdXJjZXMiOlsiYmFzZS1jaGFydC5kaXJlY3RpdmUuZC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTs7Ozs7QUFJQTs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7QUE0REEiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBEb0NoZWNrLCBFbGVtZW50UmVmLCBFdmVudEVtaXR0ZXIsIE9uQ2hhbmdlcywgT25EZXN0cm95LCBPbkluaXQsIFNpbXBsZUNoYW5nZXMgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCB7IENvbG9yIH0gZnJvbSAnLi9jb2xvcic7XG5pbXBvcnQgeyBUaGVtZVNlcnZpY2UgfSBmcm9tICcuL3RoZW1lLnNlcnZpY2UnO1xuaW1wb3J0IHsgQ2hhcnRDb25maWd1cmF0aW9uLCBDaGFydERhdGFTZXRzLCBDaGFydE9wdGlvbnMsIENoYXJ0UG9pbnQsIENoYXJ0VHlwZSwgUGx1Z2luU2VydmljZUdsb2JhbFJlZ2lzdHJhdGlvbiwgUGx1Z2luU2VydmljZVJlZ2lzdHJhdGlvbk9wdGlvbnMgfSBmcm9tICdjaGFydC5qcyc7XG5leHBvcnQgZGVjbGFyZSB0eXBlIFNpbmdsZURhdGFTZXQgPSBBcnJheTxudW1iZXIgfCBudWxsIHwgdW5kZWZpbmVkIHwgbnVtYmVyW10+IHwgQ2hhcnRQb2ludFtdO1xuZXhwb3J0IGRlY2xhcmUgdHlwZSBNdWx0aURhdGFTZXQgPSBTaW5nbGVEYXRhU2V0W107XG5leHBvcnQgZGVjbGFyZSB0eXBlIFNpbmdsZU9yTXVsdGlEYXRhU2V0ID0gU2luZ2xlRGF0YVNldCB8IE11bHRpRGF0YVNldDtcbmV4cG9ydCBkZWNsYXJlIHR5cGUgUGx1Z2luU2VydmljZUdsb2JhbFJlZ2lzdHJhdGlvbkFuZE9wdGlvbnMgPSBQbHVnaW5TZXJ2aWNlR2xvYmFsUmVnaXN0cmF0aW9uICYgUGx1Z2luU2VydmljZVJlZ2lzdHJhdGlvbk9wdGlvbnM7XG5leHBvcnQgZGVjbGFyZSB0eXBlIFNpbmdsZUxpbmVMYWJlbCA9IHN0cmluZztcbmV4cG9ydCBkZWNsYXJlIHR5cGUgTXVsdGlMaW5lTGFiZWwgPSBzdHJpbmdbXTtcbmV4cG9ydCBkZWNsYXJlIHR5cGUgTGFiZWwgPSBTaW5nbGVMaW5lTGFiZWwgfCBNdWx0aUxpbmVMYWJlbDtcbmV4cG9ydCBkZWNsYXJlIGNsYXNzIEJhc2VDaGFydERpcmVjdGl2ZSBpbXBsZW1lbnRzIE9uQ2hhbmdlcywgT25Jbml0LCBPbkRlc3Ryb3ksIERvQ2hlY2sge1xuICAgIHByaXZhdGUgZWxlbWVudDtcbiAgICBwcml2YXRlIHRoZW1lU2VydmljZTtcbiAgICBkYXRhOiBTaW5nbGVPck11bHRpRGF0YVNldDtcbiAgICBkYXRhc2V0czogQ2hhcnREYXRhU2V0c1tdO1xuICAgIGxhYmVsczogTGFiZWxbXTtcbiAgICBvcHRpb25zOiBDaGFydE9wdGlvbnM7XG4gICAgY2hhcnRUeXBlOiBDaGFydFR5cGU7XG4gICAgY29sb3JzOiBDb2xvcltdO1xuICAgIGxlZ2VuZDogYm9vbGVhbjtcbiAgICBwbHVnaW5zOiBQbHVnaW5TZXJ2aWNlR2xvYmFsUmVnaXN0cmF0aW9uQW5kT3B0aW9uc1tdO1xuICAgIGNoYXJ0Q2xpY2s6IEV2ZW50RW1pdHRlcjx7XG4gICAgICAgIGV2ZW50PzogTW91c2VFdmVudDtcbiAgICAgICAgYWN0aXZlPzoge31bXTtcbiAgICB9PjtcbiAgICBjaGFydEhvdmVyOiBFdmVudEVtaXR0ZXI8e1xuICAgICAgICBldmVudDogTW91c2VFdmVudDtcbiAgICAgICAgYWN0aXZlOiB7fVtdO1xuICAgIH0+O1xuICAgIGN0eDogc3RyaW5nO1xuICAgIGNoYXJ0OiBDaGFydDtcbiAgICBwcml2YXRlIG9sZDtcbiAgICBwcml2YXRlIHN1YnM7XG4gICAgLyoqXG4gICAgICogUmVnaXN0ZXIgYSBwbHVnaW4uXG4gICAgICovXG4gICAgc3RhdGljIHJlZ2lzdGVyUGx1Z2luKHBsdWdpbjogUGx1Z2luU2VydmljZUdsb2JhbFJlZ2lzdHJhdGlvbkFuZE9wdGlvbnMpOiB2b2lkO1xuICAgIHN0YXRpYyB1bnJlZ2lzdGVyUGx1Z2luKHBsdWdpbjogUGx1Z2luU2VydmljZUdsb2JhbFJlZ2lzdHJhdGlvbkFuZE9wdGlvbnMpOiB2b2lkO1xuICAgIGNvbnN0cnVjdG9yKGVsZW1lbnQ6IEVsZW1lbnRSZWYsIHRoZW1lU2VydmljZTogVGhlbWVTZXJ2aWNlKTtcbiAgICBuZ09uSW5pdCgpOiB2b2lkO1xuICAgIHByaXZhdGUgdGhlbWVDaGFuZ2VkO1xuICAgIG5nRG9DaGVjaygpOiB2b2lkO1xuICAgIGNvcHlMYWJlbChhOiBMYWJlbCk6IExhYmVsO1xuICAgIGxhYmVsc0VxdWFsKGE6IExhYmVsLCBiOiBMYWJlbCk6IGJvb2xlYW47XG4gICAgY29weUNvbG9yKGE6IENvbG9yKTogQ29sb3I7XG4gICAgY29sb3JzRXF1YWwoYTogQ29sb3IsIGI6IENvbG9yKTogYm9vbGVhbjtcbiAgICB1cGRhdGVDb2xvcnMoKTogdm9pZDtcbiAgICBuZ09uQ2hhbmdlcyhjaGFuZ2VzOiBTaW1wbGVDaGFuZ2VzKTogdm9pZDtcbiAgICBuZ09uRGVzdHJveSgpOiB2b2lkO1xuICAgIHVwZGF0ZShkdXJhdGlvbj86IGFueSk6IHt9O1xuICAgIGhpZGVEYXRhc2V0KGluZGV4OiBudW1iZXIsIGhpZGRlbjogYm9vbGVhbik6IHZvaWQ7XG4gICAgaXNEYXRhc2V0SGlkZGVuKGluZGV4OiBudW1iZXIpOiBib29sZWFuO1xuICAgIHRvQmFzZTY0SW1hZ2UoKTogc3RyaW5nO1xuICAgIGdldENoYXJ0Q29uZmlndXJhdGlvbigpOiBDaGFydENvbmZpZ3VyYXRpb247XG4gICAgZ2V0Q2hhcnRCdWlsZGVyKGN0eDogc3RyaW5nKTogQ2hhcnQ7XG4gICAgc21hcnRNZXJnZShvcHRpb25zOiBhbnksIG92ZXJyaWRlczogYW55LCBsZXZlbD86IG51bWJlcik6IGFueTtcbiAgICBwcml2YXRlIGlzTXVsdGlMaW5lTGFiZWw7XG4gICAgcHJpdmF0ZSBqb2luTGFiZWw7XG4gICAgcHJpdmF0ZSBwcm9wYWdhdGVEYXRhc2V0c1RvRGF0YTtcbiAgICBwcml2YXRlIHByb3BhZ2F0ZURhdGFUb0RhdGFzZXRzO1xuICAgIHByaXZhdGUgaXNNdWx0aURhdGFTZXQ7XG4gICAgcHJpdmF0ZSBnZXREYXRhc2V0cztcbiAgICBwcml2YXRlIHJlZnJlc2g7XG59XG4iXX0=