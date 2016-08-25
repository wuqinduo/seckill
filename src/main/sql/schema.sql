-- ���ݿ��ʼ���ű�

--�������ݿ�
CREATE DATABASE seckill;

--ʹ�����ݿ�
USE seckill;

--������ɱ����
CREATE TABLE seckill(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���id',
  `name` VARCHAR(120) NOT NULL COMMENT '��Ʒ����',
  `number` int NOT NULL COMMENT '�������',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `start_time` TIMESTAMP NOT NULL COMMENT '��ɱ��ʼʱ��',
  `end_time` TIMESTAMP NOT NULL COMMENT '��ɱ����ʱ��',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  key idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET = utf8 COMMENT='��ɱ����';

--��ʼ������
INSERT INTO
  seckill(name, number, start_time, end_time)
VALUES
  ('1000Ԫ��ɱiPhone SE', 100, '2016-06-09 00:00:00', '2016-06-10 00:00:00'),
  ('500Ԫ��ɱiPad2', 200, '2016-06-09 00:00:00', '2016-06-10 00:00:00'),
  ('300Ԫ��ɱС��4', 300, '2016-06-09 00:00:00', '2016-06-10 00:00:00'),
  ('200Ԫ��ɱ����Note', 400, '2016-06-09 00:00:00', '2016-06-10 00:00:00'),
  ('100Ԫ��ɱKindle', 500, '2016-06-09 00:00:00', '2016-06-10 00:00:00');

-- ��ɱ�ɹ���ϸ��
-- �û���¼��֤��ص���Ϣ
CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '��ɱ��Ʒid',
  `user_phone` BIGINT NOT NULL COMMENT '�û��ֻ���',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '״̬��ʶ��-1:��Ч��0:�ɹ�,1:�Ѹ��',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY(seckill_id, user_phone),/*��������  ������ͬʱ������*/
  key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET = utf8 COMMENT '��ɱ�ɹ���ϸ��';

--�������ݿ����̨
--mysql -uroot -proot