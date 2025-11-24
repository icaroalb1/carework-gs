CREATE TABLE IF NOT EXISTS users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email VARCHAR(255) UNIQUE NOT NULL,
  full_name VARCHAR(255),
  password_hash VARCHAR(512),
  api_key VARCHAR(255),
  role VARCHAR(50),
  enabled BOOLEAN NOT NULL DEFAULT true,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

CREATE TABLE IF NOT EXISTS tips (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title VARCHAR(255),
  content TEXT NOT NULL,
  author_id UUID,
  active BOOLEAN NOT NULL DEFAULT true,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_tips_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_tips_created_at ON tips(created_at);

CREATE TABLE IF NOT EXISTS mood_checkins (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  mood SMALLINT NOT NULL CHECK (mood BETWEEN 1 AND 5),
  stress SMALLINT NOT NULL CHECK (stress BETWEEN 1 AND 5),
  sleep_quality SMALLINT CHECK (sleep_quality BETWEEN 1 AND 5),
  note TEXT,
  shift VARCHAR(50),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_checkins_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_checkins_user_created_at ON mood_checkins(user_id, created_at);

CREATE TABLE IF NOT EXISTS checkin_event_logs (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  checkin_id UUID,
  event_type VARCHAR(100),
  payload JSONB,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_event_checkin FOREIGN KEY (checkin_id) REFERENCES mood_checkins (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_eventlogs_checkin_id ON checkin_event_logs(checkin_id);

CREATE TABLE IF NOT EXISTS app_config (
  key VARCHAR(255) PRIMARY KEY,
  value TEXT,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

ALTER TABLE public.users
  RENAME COLUMN full_name TO name;

ALTER TABLE public.users
  RENAME COLUMN password_hash TO password;

ALTER TABLE public.users
  ADD COLUMN IF NOT EXISTS team varchar(50);

ALTER TABLE tips
  RENAME COLUMN content TO description;

ALTER TABLE tips DROP CONSTRAINT IF EXISTS tips_pkey;
ALTER TABLE tips ALTER COLUMN id TYPE bigint USING (nextval('tips_seq'));