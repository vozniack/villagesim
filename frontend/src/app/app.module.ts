import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './view/home/home.component';
import {ToolbarComponent} from './shared/toolbar/toolbar.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {HttpClientModule} from "@angular/common/http";
import {WorldMapComponent} from './view/world-map/world-map.component';
import {ActionBarComponent} from './shared/action-bar/action-bar.component';
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {ModalGenerate} from "./shared/modals/generate-modal/modal-generate.component";
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {StatisticsComponent} from './view/statistics/statistics.component';
import {LogsComponent} from './view/logs/logs.component';
import {WoodChartComponent} from './view/statistics/wood-chart/wood-chart.component';
import {NgxEchartsModule} from "ngx-echarts";

import * as echarts from 'echarts';
import {RockChartComponent} from './view/statistics/rock-chart/rock-chart.component';
import {FoodChartComponent} from './view/statistics/food-chart/food-chart.component';
import {ScrollingModule} from "@angular/cdk/scrolling";
import {MatListModule} from "@angular/material/list";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ToolbarComponent,
    WorldMapComponent,
    ActionBarComponent,
    ModalGenerate,
    StatisticsComponent,
    LogsComponent,
    WoodChartComponent,
    RockChartComponent,
    FoodChartComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatCardModule,
        HttpClientModule,
        MatTooltipModule,
        MatProgressSpinnerModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        FormsModule,
        NgxEchartsModule.forRoot({
            echarts
        }),
        ScrollingModule,
        MatListModule
    ],
  providers: [{
    provide: MatDialogRef,
    useValue: {}
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
