import json

import phonenumber_field
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from phonenumber_field.modelfields import PhoneNumberField
from kebabmania.models import Usuario


@csrf_exempt
def editarborrar(request, tlf):
    if tlf == None:
        return JsonResponse({"error": "Missing a param"}, status=400)

    request_token = request.META.get('HTTP_SESSION_TOKEN')
    if request_token is None:
        return JsonResponse({"error": "Unauthorized"}, status=401)


    try:
        user_row = Usuario.objects.get(token= request_token)
    except Usuario.DoesNotExist:
        return JsonResponse({"error": "Unauthorized"}, status=401)

    if request.method == 'PATCH':
        json_body = json.loads(request.body)
        json_nombre = json_body['nombre']
        json_tlf = json_body['tlf']

        usuario_modificado = Usuario.objects.filter(telefono = json_tlf).update(nombre = json_nombre)

        return JsonResponse({'edited': 'True'}, status=201)
    elif request.method == 'DELETE':
        try:
            Usuario.objects.get(telefono= tlf)
        except Usuario.DoesNotExist:
            return JsonResponse({"error": "not found"}, status=404)
        Usuario.objects.filter(telefono=tlf).delete()
        return JsonResponse({"Done": "Changes commited"}, status=201)
    else:
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)




def crear(request):

    if request.method != 'POST':
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)


    body = json.loads(request.body)
    new_nombre = body.get('nombre', None)
    new_telefono = body.get('tlf', None)

    if new_nombre is None or new_telefono is None:
        return JsonResponse({'error': 'Missing parameter in request body'}, status=400)

    if len(new_telefono) != 9 :
        return JsonResponse({"error": "Telefono invalido"}, status=409)


    if Usuario.objects.filter(telefono=new_telefono).exists():
        return JsonResponse({"error": "Telefono already exists"}, status=409)

    object = Usuario.objects.order_by("-id").first()
    if object is None:
        new_id = 1  # Si es nulo significa que no hay ningún socio y le asigna el número 1
    else:
        new_id = object.getId() + 1  # De no ser nulo coge el mayor número de socio, le suma 1 y lo

    new_usuario = Usuario(nombre=new_telefono, telefono=new_telefono, token=None)
    new_usuario.save()

    return JsonResponse({'created': 'True'}, status=201)