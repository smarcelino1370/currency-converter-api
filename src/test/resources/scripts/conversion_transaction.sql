INSERT INTO users (id, username, password) VALUES ('a8245833-b413-4976-a890-e5a0c78dc5b9', 'it', '83cb40d1beaba8036da4c2cf2e62fd8d54557afb1c136f281eb27e1086519bf31c7de995b0211e34');
INSERT INTO user_role (id, user_id, role) VALUES ('53893e29-e6b9-46fb-a57f-9f3ececbf8b0', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'ADMIN');

INSERT INTO conversion_transaction
       (id                                    , user_id                               , origin, destination, rate       , transaction_date, amount)
VALUES ('bd5c8763-d6cb-4892-8617-b76881d453fd', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'BRL' , 'EUR'      ,   0.180000 , '2024-05-01'    , 100),
       ('2472086d-453f-4eea-92a4-51321bb9a28e', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'BRL' , 'JPY'      ,  30.130000 , '2024-05-02'    , 100),
       ('e1fdcfc2-a9a0-4018-b2f0-00bafc29e0c4', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'BRL' , 'USD'      ,   0.190000 , '2024-05-03'    , 100),
       ('36ccd92a-85ea-4992-9cdf-4ab91f822122', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'EUR' , 'BRL'      ,   5.640000 , '2024-05-04'    , 100),
       ('b6e1fb21-91c9-40f6-b8ae-529d037868d6', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'EUR' , 'JPY'      , 169.870000 , '2024-05-05'    , 100),
       ('c30d6f02-650b-4f2a-a2c5-6c454203adca', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'EUR' , 'USD'      ,  01.080000 , '2024-05-06'    , 100),
       ('3a2f3ddd-8dd5-4747-8ea0-6da5eca7cd34', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'JPY' , 'BRL'      ,  33.000000 , '2024-05-07'    , 100),
       ('9addc1d0-1f06-48a3-9327-51c17e5803ae', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'JPY' , 'EUR'      ,  59.000000 , '2024-05-08'    , 100),
       ('5a64dfce-5f5b-46a3-9734-462ac4f284f2', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'JPY' , 'USD'      ,  64.000000 , '2024-05-09'    , 100),
       ('75332183-1f62-4718-890c-bdf8a4305c88', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'USD' , 'BRL'      ,   5.200000 , '2024-05-10'    , 100),
       ('cb55275d-0dd9-4f1b-b451-b3c2d55c10ee', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'USD' , 'EUR'      ,   0.920000 , '2024-05-11'    , 100),
       ('4c671f2f-e6d5-4974-b222-0cd4771b2309', 'a8245833-b413-4976-a890-e5a0c78dc5b9', 'USD' , 'JPY'      , 156.820000 , '2024-05-12'    , 100);