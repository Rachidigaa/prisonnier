import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { GameService } from '../../services/game.service';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  standalone: true,
  imports: [FormsModule, NgIf],
  styleUrls: ['./create-game.component.css'],
})
export class CreateGameComponent {
  totalRounds: number = 1;
  gameId: number | null = null;
  playerName: string = '';
  isGameCreated: boolean = false;
  errorMessage: string | null = null;

  constructor(private gameService: GameService, private router: Router) {}

  createGame() {
    this.errorMessage = null;
    this.gameService.createGame(this.totalRounds).subscribe(
      (game) => {
        this.gameId = game.id;
        this.isGameCreated = true;
      },
      (error) => {
        this.errorMessage = 'Failed to create game. Please try again.';
      }
    );
  }

  joinGame() {
    if (this.gameId && this.playerName.trim()) {
      this.errorMessage = null;
      this.gameService
        .joinGame(this.gameId, this.playerName.trim())
        .subscribe(
          (response) => {
            console.log(response);
            
            this.router.navigate(['/waiting-room'], {
              queryParams: { gameId: response.game.id, playerId: response.playerId },
            });
          },
          (error) => {
            this.errorMessage = 'Failed to join game. Please check the details.';
          }
        );
    } else {
      this.errorMessage = 'Please enter a valid player name.';
    }
  }
  
}
