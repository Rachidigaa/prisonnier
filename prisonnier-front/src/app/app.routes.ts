import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CreateGameComponent } from './components/create-game/create-game.component';
import { WaitingRoomComponent } from './components/waiting-room/waiting-room.component';
import { JoinGameComponent } from './components/join-game/join-game.component';
import { GameComponent } from './components/game/game.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full', 
    },    
    {
        path : 'home',
        component : HomeComponent
    },
    {
        path : 'create',
        component : CreateGameComponent
    },
    { 
        path: 'waiting-room',
        component: WaitingRoomComponent
    },
    {
        path: 'join',
        component: JoinGameComponent
    },
    {
        path: 'game/:gameId/:playerId',
        component: GameComponent
    }
];
