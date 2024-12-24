import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../../services/game.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements OnInit, OnDestroy {
  gameId!: number;
  playerName!: string;
  playerId!: number;
  currentRound: number = 0;
  totalRounds: number = 0;
  isGameFinished: boolean = false;
  isWaiting: boolean = false;
  players: any[] = [];
  currentPlayer: any;
  strategies: string[] = [];
  selectedStrategy: string = '';
  isDropdownVisible: boolean = false;
  intervalId: any; // Stocker la référence de l'intervalle

  // Gestion des images
  defaultImage: string = 'assets/jouer1.jpg';
  coopImage: string = 'assets/cooperer1.jpg';
  betrayImage: string = 'assets/trahir1.jpg';
  currentImage: string = this.defaultImage;
  animationClass: string = 'animated-image';
  winnerImage: string = 'assets/gagnant.jpg';
  loserImage: string = 'assets/perdant.jpg';
  endGameImage: string = this.defaultImage;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService
  ) {}

  ngOnInit(): void {
    this.gameId = Number(this.route.snapshot.paramMap.get('gameId'));
    this.playerName = this.route.snapshot.queryParamMap.get('playerName') || '';
    this.playerId = Number(this.route.snapshot.paramMap.get('playerId'));

    this.loadGameStatus();
    this.pollGameStatus();
    this.loadStrategies();
  }

  ngOnDestroy(): void {
    this.clearPolling(); // Arrêter le polling lorsque le composant est détruit
  }

  loadStrategies(): void {
    this.gameService.getStrategies().subscribe((strategies) => {
      this.strategies = strategies;
    });
  }

  toggleDropdown(): void {
    this.isDropdownVisible = !this.isDropdownVisible;
  }

  abandonGame(): void {
    if (this.selectedStrategy) {
      this.gameService.quitGame(this.playerId, this.selectedStrategy).subscribe(
        (response: any) => {
          alert(response.message); // Affiche le message du backend
          this.router.navigate(['/home']); // Retour à l'accueil
        },
        (error) => {
          console.error('Erreur lors de l’abandon :', error);
        }
      );
    } else {
      alert('Veuillez sélectionner une stratégie avant de quitter.');
    }
  }

  loadGameStatus(): void {
    this.gameService.getGameStatus(this.gameId).subscribe((status) => {
      this.currentRound = status.currentRound;
      this.totalRounds = status.rounds;
      this.isGameFinished = status.status === 'FINISHED';
      this.players = status.players;

      this.currentPlayer = this.players.find(
        (player: any) => player.id === this.playerId
      );

      if (this.currentPlayer && !this.isGameFinished) {
        this.isWaiting = this.currentPlayer.currentMove !== null;
      }

      if (this.isGameFinished) {
        this.clearPolling(); // Arrêtez le polling si la partie est terminée
        const maxScore = Math.max(...this.players.map((player: any) => player.score));
        const currentPlayerScore = this.currentPlayer ? this.currentPlayer.score : 0;
        this.endGameImage =
          currentPlayerScore === maxScore ? this.winnerImage : this.loserImage;
      }
    });
  }

  pollGameStatus(): void {
    this.intervalId = setInterval(() => {
      if (!this.isGameFinished) {
        this.loadGameStatus();
      }
    }, 3000);
  }

  clearPolling(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId); // Supprime l'intervalle
    }
  }

  submitMove(move: string): void {
    if (this.isWaiting) return;

    this.isWaiting = true;
    this.gameService.playRound(this.gameId, this.playerId, move).subscribe(
      (response: any) => {
        console.log(response.message); // Affiche le message dans la console
        this.loadGameStatus();
      },
      (error) => {
        console.error('Erreur lors de la soumission du mouvement', error);
        this.isWaiting = false; // Permet de rejouer en cas d'erreur
      }
    );
  }

  changeImage(action: string): void {
    if (action === 'COOPERER') {
      this.applyAnimation(this.coopImage);
    } else if (action === 'TRAHIR') {
      this.applyAnimation(this.betrayImage);
    }
  }

  resetImage(): void {
    this.applyAnimation(this.defaultImage);
  }

  applyAnimation(newImage: string): void {
    this.animationClass = '';
    setTimeout(() => {
      this.currentImage = newImage;
      this.animationClass = 'animated-image';
    }, 50);
  }

  quitGame(): void {
    this.router.navigate(['/home']);
  }
}
