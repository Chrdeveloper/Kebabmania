# Generated by Django 4.2.1 on 2023-05-28 15:37

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('kebabmania', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='usuario',
            name='token',
            field=models.CharField(max_length=20, null=True, unique=True),
        ),
    ]
