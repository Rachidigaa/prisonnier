import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../../services/game.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Decision, Game, Strategie } from '../../model/model';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements OnInit, OnDestroy {
  gameId: number | null = null;
  game: Game | null = null;
  playerId: number | null = null;
  selectedStrategy: Strategie | null = null;
  strategies = Object.values(Strategie);
  errorMessage: string | null = null;
  gameStatusInterval: any;
  showQuitModal = false;
  hasPlayed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService
  ) {}

  ngOnInit() {
    this.gameId = Number(this.route.snapshot.paramMap.get('gameId'));
    this.playerId = Number(this.route.snapshot.paramMap.get('playerId'));

    if (this.gameId) {
      this.loadGameStatus();
      this.startGameStatusCheck();
    }
  }
  openQuitModal() {
    this.showQuitModal = true;
  }

  closeQuitModal() {
    this.showQuitModal = false;
    this.selectedStrategy = null;
  }
  ngOnDestroy() {
    if (this.gameStatusInterval) {
      clearInterval(this.gameStatusInterval);
    }
  }

  getPlayer() {
    return this.game?.players.find((player) => player.id === this.playerId);
  }

  getEnemies() {
    return this.game?.players.filter((player) => player.id !== this.playerId) || [];
  }

  loadGameStatus() {
    this.gameService.getGameStatus(this.gameId!,this.playerId!).subscribe(
      (game) => {
        this.game = game;
        
        const currentPlayer = this.getPlayer();
        if (currentPlayer) {
          this.hasPlayed = currentPlayer.hasPlayed;
          console.log(this.hasPlayed);
          
        }
        if (game.state === 'FINISHED') {
          alert('The game has ended!');
          clearInterval(this.gameStatusInterval);
        }
      },
      (error) => {
        this.errorMessage = 'Failed to load game status. Please try again.';
      }
    );
  }
  goToHome() {
    this.router.navigate(['/home']);
  }
  
  createNewGame() {
    this.router.navigate(['/create']);
  }
  
  makeMove(decision: string) {
    if (this.gameId && this.playerId && !this.hasPlayed) {
      this.gameService.playRound(this.gameId, this.playerId, this.getDecisionFromString(decision)).subscribe(
        () => {
          this.loadGameStatus();
        },
        (error) => {
          this.errorMessage = 'Failed to make a move. Please try again.';
        }
      );
    }
  }

  getDecisionFromString(decisionString: string): Decision {
    switch (decisionString.toUpperCase()) {
      case 'COOPERER':
        return Decision.COOPERER;
      case 'TRAHIR':
        return Decision.TRAHIR;
      case 'PARTIR':
        return Decision.PARTIR;
      default:
        return Decision.COOPERER;
    }
  }
  confirmQuit() {
    if (this.selectedStrategy) {
      this.gameService.abandonGame(this.game!.id, this.getPlayer()!.id, this.selectedStrategy).subscribe(
        () => {
          this.closeQuitModal();
          this.router.navigate(["/home"]);
        },
        (error) => {
          console.error('Échec de l\'abandon du jeu.', error);
        }
      );
    } else {
      alert('Veuillez sélectionner une stratégie avant de confirmer.');
    }
  }

  startGameStatusCheck() {
    this.gameStatusInterval = setInterval(() => {
      this.loadGameStatus();
    }, 2000);
  }
}
