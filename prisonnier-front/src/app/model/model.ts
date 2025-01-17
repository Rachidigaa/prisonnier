export interface Game {
  id: number;
  totalRounds: number;
  currentRound: number;
  state: 'WAITING_FOR_PLAYERS' | 'IN_PROGRESS' | 'FINISHED';
  players: Player[];
  rounds: Round[];
}

export interface Player {
  id: number;
  username: string;
  score: number;
  hasPlayed: boolean;
  strategy: Strategie | null; 
}

export interface Round {
  id: number;
  decisions: { [playerId: number]: Decision };
  scores: { [playerId: number]: number };
}

export enum Strategie {
  DONNANT_DONNANT = 'DONNANTDONNANT',
  RANCUNIER = 'RANCUNIER',
  TOUJOURS_COOPERER = 'TOUJOURSCOOPERER',
  TOUJOURS_TRAHIR = 'TOUJOURSTRAHIR',
  ALEATOIRE = 'ALEATOIRE',
  DONNANT_DONNANT_ALEATOIRE = 'DONNANTDONNANTALEATOIRE',
  DONNANT_POUR_DEUX_DONNANT_ALEATOIRE = 'DONNANTPOURDEUXDONNANTALEATOIRE',
  DONNANT_DONNANT_SOUPCONNEUX = 'DONNANTDONNANTSOUPCONNEUX',
  DONNANT_POUR_DEUX_DONNANT = 'DONNANTPOURDEUXDONNANT',
  PACIFICATEUR_NAIF = 'PACIFICATEURNAIF',
  SONDEUR_NAIF = 'SONDEURNAIF',
  GRADUEL = 'GRADUEL',
  SONDEUR_REPENTANT = 'SONDEURREPENTANT',
}

export enum Decision {
  COOPERER = 'COOPERER',
  TRAHIR = 'TRAHIR',
  PARTIR = 'PARTIR',
}
