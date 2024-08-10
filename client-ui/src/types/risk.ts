export type Risk = {
    id: number;
    level: RiskLevel;
}

export type RiskLevel = 'NONE' | 'BORDERLINE' | 'IN_DANGER' | 'EARLY_ONSET';
