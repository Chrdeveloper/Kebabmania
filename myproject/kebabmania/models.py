from django.db import models

class Ciudad(models.Model):
    nom_ciudad = models.CharField(max_length=20)

    def to_json(self):
        return {'nom_ciudad': self.nom_ciudad}


class Kebab(models.Model):
    nombre = models.CharField(max_length=20)
    lugar = models.CharField(max_length=20)
    id_ciudad= models.ForeignKey(Ciudad, on_delete=models.CASCADE)
    def to_json(self):
        return {
            "id":self.id,
            "nombre": self.nombre,
            "id_ciudad": self.id_ciudad,
            "lugar": self.lugar,
        }


class Plato(models.Model):
    nombre = models.CharField(max_length=20)
    descripcion = models.CharField(max_length=20)

    def to_json(self):
        return {
            "id": self.id,
            "nombre": self.nombre,
            "descripcion": self.descripcion,
        }


class Usuario(models.Model):
    nombre = models.CharField(max_length=100)
    telefono = models.CharField(max_length=10, unique=True)
    token = models.CharField(unique=True, max_length=20, null=True)
    def to_json(self):
        return {
            "id": self.id,
            "nombre": self.nombre,
            "telefono": self.telefono,
        }


class Opiniones(models.Model):
    id_usuario = models.ForeignKey(Usuario, on_delete=models.CASCADE)
    id_kebab = models.ForeignKey(Kebab,on_delete=models.CASCADE)
    def to_json(self):
        return {
            "id_usuario": self.id_usuario_id,
            "id_actividad": self.id_kebab_id
        }

